package com.github.inorichi.marvel.remote

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
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy

@Suppress("BlockingMethodInNonBlockingContext")
class MarvelApiTest : FunSpec({

  val okHttpClient = OkHttpClient()

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
    result.data.count.shouldBe(20)
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
    // TODO
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

  test("throws unreachable") {
    mockServer.shutdown()

    shouldThrow<MarvelApiException.UnreachableError> {
      marvelApi.getCharacters(0)
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
