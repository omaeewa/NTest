package com.miracle.natifetest.data.di

import com.miracle.natifetest.data.repository.GifsRepositoryImpl
import com.miracle.natifetest.data.service.GiphyService
import com.miracle.natifetest.domain.repository.GifsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(giphyService: GiphyService): GifsRepository =
        GifsRepositoryImpl(giphyService)

}