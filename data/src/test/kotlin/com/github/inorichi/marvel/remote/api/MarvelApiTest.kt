package com.github.inorichi.marvel.remote.api

import com.github.inorichi.marvel.data.remote.api.MarvelApi
import com.github.inorichi.marvel.data.remote.api.MarvelApiConstants
import com.github.inorichi.marvel.data.remote.api.MarvelApiException
import com.github.inorichi.marvel.data.remote.api.MarvelApiInterceptor
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import java.net.InetAddress
import java.net.UnknownHostException
import java.time.Duration

@Suppress("BlockingMethodInNonBlockingContext")
class MarvelApiTest : FunSpec({

  val okHttpClient = OkHttpClient.Builder()
    .readTimeout(Duration.ofMillis(100))
    .build()

  lateinit var mockServer: MockWebServer
  lateinit var marvelApi: MarvelApi

  beforeTest {
    mockServer = MockWebServer().also {
      it.start()
    }
    marvelApi = MarvelApi.create(
      okHttpClient = okHttpClient,
      marvelApiInterceptor = MarvelApiInterceptor(),
      baseUrl = mockServer.url("/").toString()
    )
  }

  afterTest {
    mockServer.shutdown()
  }

  test("reads content from file") {
    val content = mockApiResponseFromFile("marvel-api/character-details.json")
    content.shouldNotBeBlank()
  }

  test("returns the list of characters") {
    val content = mockApiResponseFromFile("marvel-api/character-list.json")

    val response = MockResponse()
      .setResponseCode(200)
      .setBody(content)
    mockServer.enqueue(response)

    val result = marvelApi.getCharacters(0)
    result.code.shouldBe(200)
    result.data.offset.shouldBe(0)
    result.data.results.shouldHaveSize(20)
  }

  test("returns the details of a character") {
    val content = mockApiResponseFromFile("marvel-api/character-details.json")

    val response = MockResponse()
      .setResponseCode(200)
      .setBody(content)
    mockServer.enqueue(response)

    val result = marvelApi.getCharacterDetails(1011334)
    result.code.shouldBe(200)
    result.data.results.shouldHaveSize(1)
    val character = result.data.results.first()
    character.name.shouldBe("3-D Man")
    character.comics.items.shouldHaveSize(12)
    character.series.items.shouldHaveSize(3)
  }

  test("throws unauthorized error when API constants are not defined") {
    mockkObject(MarvelApiConstants)
    every { MarvelApiConstants.APIKEY } returns ""

    val content = mockApiResponseFromFile("marvel-api/character-details.json")

    val response = MockResponse()
      .setResponseCode(200)
      .setBody(content)
    mockServer.enqueue(response)

    shouldThrow<MarvelApiException.UnauthorizedError> {
      marvelApi.getCharacters(0)
    }

    unmockkObject(MarvelApiConstants)
  }

  test("throws a client error") {
    val content = mockApiResponseFromFile("marvel-api/character-list-error.json")

    val response = MockResponse()
      .setResponseCode(409)
      .setBody(content)
    mockServer.enqueue(response)

    val error = shouldThrow<MarvelApiException.ClientError> {
      marvelApi.getCharacters(0)
    }
    error.code.shouldBe(409)
  }

  test("throws a server error") {
    val response = MockResponse()
      .setResponseCode(500)
      .setBody("Internal Server Error")
    mockServer.enqueue(response)

    val error = shouldThrow<MarvelApiException.ServerError> {
      marvelApi.getCharacters(0)
    }
    error.code.shouldBe(500)
  }

  test("throws unreachable when there's a ConnectionException") {
    mockServer.shutdown()

    shouldThrow<MarvelApiException.UnreachableError> {
      marvelApi.getCharacters(0)
    }
  }

  test("throws unreachable when there's a SocketTimeoutException") {
    val response = MockResponse()
      .setSocketPolicy(SocketPolicy.NO_RESPONSE)
    mockServer.enqueue(response)

    shouldThrow<MarvelApiException.UnreachableError> {
      marvelApi.getCharacters(0)
    }
  }

  test("throws unreachable when there's a UnknownHostException") {
    val okHttpClient2 = okHttpClient.newBuilder()
      .dns(object : Dns {
        override fun lookup(hostname: String): List<InetAddress> {
          throw UnknownHostException()
        }
      })
      .build()

    val marvelApi2 = MarvelApi.create(
      okHttpClient = okHttpClient2,
      marvelApiInterceptor = MarvelApiInterceptor(),
      baseUrl = mockServer.url("/").toString()
    )

    shouldThrow<MarvelApiException.UnreachableError> {
      marvelApi2.getCharacters(0)
    }
  }

  test("throws json decoding error on invalid content") {
    val response = MockResponse()
      .setResponseCode(200)
      .setBody("[]")
    mockServer.enqueue(response)

    shouldThrow<MarvelApiException.JsonDecodingError> {
      marvelApi.getCharacters(0)
    }
  }

  test("throws generic errors") {
    val response = MockResponse()
      .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
    mockServer.enqueue(response)

    shouldThrow<MarvelApiException.GenericError> {
      marvelApi.getCharacters(0)
    }
  }

})
