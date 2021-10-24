package com.github.inorichi.marvel.data.features.character.mapper

import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails

fun Character.toEntity(): CharacterDetails {
  return CharacterDetails(
    id = id,
    name = name,
    thumbnail = thumbnail,
    wikiUrl = null, // TODO
    comics = emptyList(),
    series = emptyList()
  )
}
