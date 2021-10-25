package com.github.inorichi.marvel.data.features.character.mapper

import com.github.inorichi.marvel.data.remote.model.GetCharactersResponse
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

fun GetCharactersResponse.toEntity(): List<CharacterOverview> {
  return data.results.map { it.toEntity() }
}

private fun GetCharactersResponse.Result.toEntity(): CharacterOverview {
  return CharacterOverview(
    id = id,
    name = name,
    thumbnail = "${thumbnail.path}/portrait_xlarge.${thumbnail.extension}"
  )
}
