package com.github.inorichi.marvel.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCharacterDetailsResponse(
  val code: Int
)
