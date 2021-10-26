package com.github.inorichi.marvel.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * A network interceptor to manage all requests sent to the Marvel API.
 */
class MarvelApiInterceptor @Inject constructor(): Interceptor {

  /**
   * Returns true if all the required API constants are not empty.
   */
  private val filledApiConstants by lazy {
    with(MarvelApiConstants) { TS.isNotEmpty() && APIKEY.isNotEmpty() && HASH.isNotEmpty() }
  }

  /**
   * Intercept all requests sent to the API, validate them and throw a [MarvelApiException] if
   * anything fails.
   */
  override fun intercept(chain: Interceptor.Chain): Response {
    try {
      if (!filledApiConstants) {
        throw MarvelApiException.UnauthorizedError("Missing API constants. They need to be added to local.properties")
      }

      val oldRequest = chain.request()

      val authorizedUrl = oldRequest.url.newBuilder()
        .addQueryParameter("ts", MarvelApiConstants.TS)
        .addQueryParameter("apikey", MarvelApiConstants.APIKEY)
        .addQueryParameter("hash", MarvelApiConstants.HASH)
        .build()

      val newRequest = oldRequest.newBuilder()
        .url(authorizedUrl)
        .build()

      val response = chain.proceed(newRequest)

      if (response.code in 400..499) {
        throw MarvelApiException.ClientError(response.code, response.message)
      } else if (response.code in 500..599) {
        throw MarvelApiException.ServerError(response.code, response.message)
      }

      return response
    } catch (error: Exception) {
      throw when (error) {
        is ConnectException -> MarvelApiException.UnreachableError(error.message.toString())
        is UnknownHostException -> MarvelApiException.UnreachableError(error.message.toString())
        is SocketTimeoutException -> MarvelApiException.UnreachableError(error.message.toString())
        is MarvelApiException -> error
        else -> MarvelApiException.GenericError(error, error.message.toString())
      }
    }
  }

}
