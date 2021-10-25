package com.github.inorichi.marvel.data.features.character.mapper

import com.github.inorichi.marvel.data.remote.model.GetCharacterDetailsResponse
import com.github.inorichi.marvel.domain.character.entity.CharacterComic
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterSeries

fun GetCharacterDetailsResponse.toEntity(): CharacterDetails? {
  return data.results.firstOrNull()?.toEntity()
}

private fun GetCharacterDetailsResponse.Result.toEntity(): CharacterDetails {
  return CharacterDetails(
    id = id,
    name = name,
    description = description,
    thumbnail = "${thumbnail.path}.${thumbnail.extension}",
    wikiUrl = urls.find { it.type == "wiki" }?.url,
    series = series.items.map { it.toEntity() },
    comics = comics.items.map { it.toEntity() },
  )
}

private fun GetCharacterDetailsResponse.Series.toEntity(): CharacterSeries {
  return CharacterSeries(
    name = name,
    url = resourceURI
  )
}

private fun GetCharacterDetailsResponse.Comic.toEntity(): CharacterComic {
  return CharacterComic(
    name = name,
    url = resourceURI
  )
}
