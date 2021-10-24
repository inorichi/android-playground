package com.github.inorichi.marvel.data.features.character.dao

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.inorichi.marvel.data.features.character.FakeCharacters
import com.github.inorichi.marvel.data.local.AppDatabase
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.robolectric.RobolectricTest
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.coroutines.flow.first

@RobolectricTest
class CharacterDaoTest : FunSpec({

  lateinit var dao: CharacterDao

  beforeTest {
    val context = ApplicationProvider.getApplicationContext<Application>()
    dao = Room.databaseBuilder(context, AppDatabase::class.java, "marvel-db-test")
      .build()
      .characterDao()
  }

  test("returns no characters") {
    val result = dao.subscribeById(1).first()
    result.shouldBeNull()
  }

  test("saves and returns character list") {
    val characters = FakeCharacters.dbPage
    dao.save(characters)

    val result = dao.subscribeById(1).first()
    result.shouldNotBeNull()
  }
})
