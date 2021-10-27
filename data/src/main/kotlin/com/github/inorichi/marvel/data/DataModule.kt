package com.github.inorichi.marvel.data

import android.app.Application
import androidx.room.Room
import com.github.inorichi.marvel.data.features.character.datasource.CharacterLocalDataSource
import com.github.inorichi.marvel.data.features.character.datasource.CharacterRemoteDataSource
import com.github.inorichi.marvel.data.features.character.repository.CharacterRepositoryImpl
import com.github.inorichi.marvel.data.local.AppDatabase
import com.github.inorichi.marvel.data.remote.api.MarvelApi
import com.github.inorichi.marvel.data.remote.api.MarvelApiInterceptor
import com.github.inorichi.marvel.domain.character.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

/**
 * Module for providing the Dagger dependencies to the rest of the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

  @Provides
  fun provideOkHttpClient(context: Application): OkHttpClient {
    val cacheDir = File(context.cacheDir, "network_cache")
    val cacheSize = 15L * 1024 * 1024 // 15 MB
    val cache = Cache(cacheDir, cacheSize)
    return OkHttpClient.Builder()
      .cache(cache)
      .build()
  }

  @Provides
  fun provideMarvelApi(
    okHttpClient: OkHttpClient,
    marvelApiInterceptor: MarvelApiInterceptor
  ): MarvelApi {
    return MarvelApi.create(okHttpClient, marvelApiInterceptor)
  }

  @Provides
  fun provideCharactersRepository(
    localDataSource: CharacterLocalDataSource,
    remoteDataSource: CharacterRemoteDataSource
  ): CharacterRepository {
    return CharacterRepositoryImpl(localDataSource, remoteDataSource)
  }

  @Provides
  fun provideDatabase(context: Application): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "marvel-db").build()
  }

}
