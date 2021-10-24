package com.github.inorichi.marvel.data.remote.api

import com.github.inorichi.marvel.data.remote.model.response.GetCharacterDetailsResponse
import com.github.inorichi.marvel.data.remote.model.response.GetCharactersResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A [Retrofit] interface to retrieve data from the Marvel API.
 */
interface MarvelApi {

  @GET("/v1/public/characters")
  suspend fun getCharacters(
    @Query("offset") offset: Int,
    @Query("limit") limit: Int = MarvelApiConstants.PAGE_LIMIT
  ): GetCharactersResponse

  @GET("/v1/public/characters/{id}")
  suspend fun getCharacterDetails(
    @Path("id") characterId: Int
  ): GetCharacterDetailsResponse

  companion object {
    @OptIn(ExperimentalSerializationApi::class)
    fun create(okHttpClient: OkHttpClient, marvelApiInterceptor: MarvelApiInterceptor): MarvelApi {
      val contentType = "application/json".toMediaType()
      val json = Json {
        ignoreUnknownKeys = true
      }
      val marvelApiClient = okHttpClient.newBuilder()
        .addInterceptor(marvelApiInterceptor)
        .build()

      return Retrofit.Builder()
        .baseUrl(MarvelApiConstants.BASE_URL)
        .client(marvelApiClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
        .create(MarvelApi::class.java)
    }
  }

}
