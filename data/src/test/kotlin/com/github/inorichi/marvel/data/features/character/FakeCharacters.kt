package com.github.inorichi.marvel.data.features.character

import com.github.inorichi.marvel.base.PageResult
import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.data.remote.model.GetCharactersResponse
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

object FakeCharacters {

  val firstPageLocal = PageResult(
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

  val dbPage = listOf(
    Character(1, "Character 1", "https://example.org/nonexistent.jpg"),
    Character(2, "Character 2", "https://example.org/nonexistent.jpg"),
  )

  val singleDetails = CharacterDetails(
    id = 1,
    name = "Character 1",
    thumbnail = "https://example.org/nonexistent.jpg",
    wikiUrl = null,
    comics = emptyList(),
    series = emptyList()
  )

  val singleDb = Character(
    id = 1,
    name = "Character 1",
    thumbnail = "https://example.org/nonexistent.jpg"
  )

  val firstPageRemote = GetCharactersResponse(
    code = 200,
    GetCharactersResponse.Data(
      offset = 0,
      limit = 20,
      total = 40,
      results = IntRange(1, 20).map { id ->
        GetCharactersResponse.Result(
          id = id,
          name = "Character $id",
          thumbnail = GetCharactersResponse.Thumbnail(
            path = "https://example.org/nonexistent",
            extension = "jpg"
          )
        )
      }
    )
  )

}
