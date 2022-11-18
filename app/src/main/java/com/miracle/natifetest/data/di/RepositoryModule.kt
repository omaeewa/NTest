package com.miracle.natifetest.data.di

import android.content.Context
import android.content.SharedPreferences
import com.miracle.natifetest.data.repository.GifsRepositoryImpl
import com.miracle.natifetest.data.repository.SharedPreferencesRepositoryImpl
import com.miracle.natifetest.data.service.GiphyService
import com.miracle.natifetest.domain.repository.GifsRepository
import com.miracle.natifetest.domain.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(giphyService: GiphyService): GifsRepository =
        GifsRepositoryImpl(giphyService)

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("sp", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPreferencesRepository(
        sharedPref: SharedPreferences,
    ): SharedPreferencesRepository = SharedPreferencesRepositoryImpl(sharedPref)
}