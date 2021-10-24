package com.github.inorichi.marvel.data.remote.api

import okio.IOException

/**
 * Set of API exceptions that can be thrown by a [MarvelApi].
 */
sealed class MarvelApiException(message: String) : IOException(message) {
  class UnauthorizedError(message: String) : MarvelApiException(message)
  class NetworkNotAvailable(message: String) : MarvelApiException(message)
  class ClientError(val code: Int, message: String) : MarvelApiException(message)
  class ServerError(val code: Int, message: String) : MarvelApiException(message)
  class UnknownError(message: String) : MarvelApiException(message)
}
