package com.github.inorichi.marvel.data.features.character.mapper

import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

/**
 * Converts the [CharacterOverview] entity to a database model.
 */
fun CharacterOverview.toDbModel(): Character {
  return Character(
    id = id,
    name = name,
    thumbnail = thumbnail
  )
}
