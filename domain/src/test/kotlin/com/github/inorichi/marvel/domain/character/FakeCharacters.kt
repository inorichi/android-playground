package com.github.inorichi.marvel.domain.character

import com.github.inorichi.marvel.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

object FakeCharacters {

  val singleDetails = CharacterDetails(
    id = 1,
    name = "Character 1",
    thumbnail = "https://example.org/nonexistent.jpg",
    wikiUrl = null,
    comics = emptyList(),
    series = emptyList()
  )

  val firstPage = PageResult(
    data = listOf(
      CharacterOverview(
        id = 1,
        name = "Character 1",
        thumbnail = "https://example.org/nonexistent.jpg"
      ),
      CharacterOverview(
        id = 2,
        name = "Character 2",
        thumbnail = "https://example.org/nonexistent.jpg"
      )
    ),
    page = 1,
    hasNextPage = true
  )

  val secondPage = PageResult(
    data = listOf(
      CharacterOverview(
        id = 3,
        name = "Character 3",
        thumbnail = "https://example.org/nonexistent.jpg"
      ),
      CharacterOverview(
        id = 4,
        name = "Character 4",
        thumbnail = "https://example.org/nonexistent.jpg"
      )
    ),
    page = 2,
    hasNextPage = false
  )
}
