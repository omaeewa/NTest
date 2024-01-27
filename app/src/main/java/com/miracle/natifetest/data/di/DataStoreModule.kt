package com.miracle.natifetest.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.miracle.natifetest.UserPreferencesProto
import com.miracle.natifetest.data.datastore.UserPreferencesSerializer
import com.miracle.natifetest.data.repository.UserDataRepositoryImpl
import com.miracle.natifetest.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferencesProto> =
        DataStoreFactory.create(serializer = userPreferencesSerializer) {
            context.dataStoreFile("user_preferences.pb")
        }

    @Singleton
    @Provides
    fun provideDataStoreRepository(dataSource: DataStore<UserPreferencesProto>): UserDataRepository =
        UserDataRepositoryImpl(dataSource)
}