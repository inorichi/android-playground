package com.github.inorichi.marvel.data.remote.api

import com.github.inorichi.marvel.data.BuildConfig

/**
 * Constants required to interact with the Marvel API, like the base url and the authentication
 * parameters.
 */
internal object MarvelApiConstants {
  const val BASE_URL = BuildConfig.API_BASE_URL
  const val TS = BuildConfig.API_TS
  const val APIKEY = BuildConfig.API_KEY
  const val HASH = BuildConfig.API_HASH

  const val PAGE_LIMIT = 20
}
