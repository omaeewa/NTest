package com.miracle.natifetest.data.di

import com.miracle.natifetest.data.DispatcherProvider
import com.miracle.natifetest.data.NatifeDispatcherProvider
import com.miracle.natifetest.data.repository.GifsRepositoryImpl
import com.miracle.natifetest.data.service.GiphyService
import com.miracle.natifetest.domain.repository.GifsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideUsersRepository(gifsRepositoryImpl: GifsRepositoryImpl): GifsRepository

    @Binds
    fun provideDispatcherProvider(impl: NatifeDispatcherProvider): DispatcherProvider

}