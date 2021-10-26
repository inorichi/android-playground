package com.github.inorichi.marvel.domain.character

import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterComic
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview
import com.github.inorichi.marvel.domain.character.entity.CharacterSeries

object FakeCharacters {

  val singleDetails = CharacterDetails(
    id = 1,
    name = "Character 1",
    description = "Some description",
    thumbnail = "https://localhost/nonexistent.jpg",
    comics = listOf(
      CharacterComic("Comic 1", "https://localhost/comic_1"),
      CharacterComic("Comic 2", "https://localhost/comic_2"),
    ),
    series = listOf(
      CharacterSeries("Series 1", "https://localhost/series_1"),
      CharacterSeries("Series 2", "https://localhost/series_2"),
    )
  )

  val firstPage = PageResult(
    data = listOf(
      CharacterOverview(
        id = 1,
        name = "Character 1",
        thumbnail = "https://localhost/nonexistent.jpg"
      ),
      CharacterOverview(
        id = 2,
        name = "Character 2",
        thumbnail = "https://localhost/nonexistent.jpg"
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
        thumbnail = "https://localhost/nonexistent.jpg"
      ),
      CharacterOverview(
        id = 4,
        name = "Character 4",
        thumbnail = "https://localhost/nonexistent.jpg"
      )
    ),
    page = 2,
    hasNextPage = false
  )
}
