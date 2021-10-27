package com.github.inorichi.marvel

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
import okhttp3.OkHttpClient
import java.net.UnknownHostException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestDataModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      // Just throw on any network connection attempt
      .dns { throw UnknownHostException() }
      .build()
  }

  @Provides
  @Singleton
  fun provideMarvelApi(
    okHttpClient: OkHttpClient,
    marvelApiInterceptor: MarvelApiInterceptor
  ): MarvelApi {
    return MarvelApi.create(okHttpClient, marvelApiInterceptor)
  }

  @Provides
  @Singleton
  fun provideCharactersRepository(
    localDataSource: CharacterLocalDataSource,
    remoteDataSource: CharacterRemoteDataSource
  ): CharacterRepository {
    return CharacterRepositoryImpl(localDataSource, remoteDataSource)
  }

  @Provides
  @Singleton
  fun provideDatabase(context: Application): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "marvel-db").build()
  }

}
