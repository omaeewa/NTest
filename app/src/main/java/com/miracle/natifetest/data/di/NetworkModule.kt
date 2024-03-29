package com.miracle.natifetest.data.di

import android.content.Context
import com.miracle.natifetest.data.hasNetwork
import com.miracle.natifetest.data.service.GiphyService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"

    @Singleton
    @Provides
    fun provideGiphyService(@ApplicationContext context: Context): GiphyService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val cacheSize = (50 * 1024 * 1024).toLong()//50MB
        val myCache = Cache(context.cacheDir, cacheSize)
        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7//7DAYS
                    ).build()


                val originalHttpUrl = request.url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", "A761SXcIMG6dNyg1gCnC1BUOC8m1wruR")
                    .build()
                request = request.newBuilder().url(url).build()

                chain.proceed(request)

            }
            .build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        return retrofit.create(GiphyService::class.java)
    }
}

