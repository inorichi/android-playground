package com.github.inorichi.marvel.data.remote.api

import com.github.inorichi.marvel.data.remote.model.response.GetCharacterDetailsResponse
import com.github.inorichi.marvel.data.remote.model.response.GetCharactersResponse
import kotlinx.serialization.ExperimentalSerializationApi
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
    fun create(
      okHttpClient: OkHttpClient,
      marvelApiInterceptor: MarvelApiInterceptor,
      baseUrl: String = MarvelApiConstants.BASE_URL
    ): MarvelApi {
      val marvelApiClient = okHttpClient.newBuilder()
        .addInterceptor(marvelApiInterceptor)
        .build()

      return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(marvelApiClient)
        .addConverterFactory(MarvelApiJsonDecoder())
        .build()
        .create(MarvelApi::class.java)
    }
  }

}
