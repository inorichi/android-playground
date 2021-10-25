package com.github.inorichi.marvel.data.features.character

import com.github.inorichi.marvel.data.features.character.model.Character
import com.github.inorichi.marvel.data.features.character.model.CharacterComic
import com.github.inorichi.marvel.data.features.character.model.CharacterSeries
import com.github.inorichi.marvel.data.features.character.model.CharacterWithRelations
import com.github.inorichi.marvel.data.remote.model.GetCharacterDetailsResponse
import com.github.inorichi.marvel.data.remote.model.GetCharactersResponse
import com.github.inorichi.marvel.domain.base.PageResult
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.entity.CharacterOverview

object FakeCharacters {

  val firstPageLocal = PageResult(
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

  val dbPage = listOf(
    Character(1, "Character 1", "https://localhost/nonexistent.jpg"),
    Character(2, "Character 2", "https://localhost/nonexistent.jpg"),
  )

  val singleDetails = CharacterDetails(
    id = 1,
    name = "Character 1",
    description = "Some description",
    thumbnail = "https://localhost/nonexistent.jpg",
    comics = emptyList(),
    series = emptyList()
  )

  val singleDb = CharacterWithRelations(
    character = Character(
      id = 1,
      name = "Character 1",
      description = "Some description",
      thumbnail = "https://localhost/nonexistent.jpg",
    ),
    comics = emptyList(),
    series = emptyList()
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
            path = "https://localhost/nonexistent",
            extension = "jpg"
          )
        )
      }
    )
  )

  val singleDetailsRemote = GetCharacterDetailsResponse(
    code = 200,
    GetCharacterDetailsResponse.Data(
      results = IntRange(1, 20).map { id ->
        GetCharacterDetailsResponse.Result(
          id = id,
          name = "Character $id",
          thumbnail = GetCharacterDetailsResponse.Thumbnail(
            path = "https://localhost/nonexistent",
            extension = "jpg"
          ),
          description = "Some description",
          comics = GetCharacterDetailsResponse.ContentItems(
            items = IntRange(1, 2).map { comicId ->
              GetCharacterDetailsResponse.Comic(
                name = "Comic $comicId",
                resourceURI = "https://localhost/comic_$comicId"
              )
            }
          ),
          series = GetCharacterDetailsResponse.ContentItems(
            items = IntRange(1, 2).map { seriesId ->
              GetCharacterDetailsResponse.Series(
                name = "Comic $seriesId",
                resourceURI = "https://localhost/comic_$seriesId"
              )
            }
          )
        )
      }
    )
  )

  fun getComicList(characterId: Int) = listOf(
    CharacterComic(characterId, "Comic 1", "https://localhost/comic_1"),
    CharacterComic(characterId, "Comic 2", "https://localhost/comic_2")
  )

  fun getSeriesList(characterId: Int) = listOf(
    CharacterSeries(characterId, "Series 1", "https://localhost/series_1"),
    CharacterSeries(characterId, "Series 2", "https://localhost/series_2")
  )

}
