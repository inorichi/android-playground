package com.github.inorichi.marvel.data.remote.api

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * A custom json decoder to wrap any decoding exception as a [MarvelApiException.JsonDecodingError]
 * using kotlin serialization.
 */
class MarvelApiJsonDecoder : Converter.Factory() {

  @OptIn(ExperimentalSerializationApi::class)
  override fun responseBodyConverter(
    type: Type,
    annotations: Array<out Annotation>,
    retrofit: Retrofit
  ): Converter<ResponseBody, *> {
    val loader = json.serializersModule.serializer(type)
    return DeserializationStrategyConverter(loader)
  }

  private fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T {
    return try {
      val string = body.string()
      json.decodeFromString(loader, string)
    } catch (error: Exception) {
      throw MarvelApiException.JsonDecodingError(error.message.orEmpty())
    }
  }

  private inner class DeserializationStrategyConverter<T>(
    val loader: DeserializationStrategy<T>
  ) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
      return fromResponseBody(loader, value)
    }
  }

  private companion object {
    val json = Json { ignoreUnknownKeys = true }
  }

}
