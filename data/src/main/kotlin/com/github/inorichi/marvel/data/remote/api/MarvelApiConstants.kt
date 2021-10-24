package com.github.inorichi.marvel.data.remote.api

import com.github.inorichi.marvel.data.BuildConfig

/**
 * Constants required to interact with the Marvel API, like the base url and the authentication
 * parameters.
 *
 * Some properties are intentionally not constant in order to mock them from tests.
 */
@Suppress("MayBeConstant")
internal object MarvelApiConstants {
  const val BASE_URL = BuildConfig.API_BASE_URL
  val TS = BuildConfig.API_TS
  val APIKEY = BuildConfig.API_KEY
  val HASH = BuildConfig.API_HASH

  const val PAGE_LIMIT = 20
}
