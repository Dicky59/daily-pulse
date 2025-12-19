package com.dicky59.dailypulse.core.di

import com.dicky59.dailypulse.ai.data.GptApi
import com.dicky59.dailypulse.articles.data.ArticleApi
import com.dicky59.dailypulse.sources.data.SourceApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GptRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient
      .Builder()
      .addInterceptor(
        HttpLoggingInterceptor().apply {
          level = HttpLoggingInterceptor.Level.BODY
        },
      ).build()

  @Provides
  @Singleton
  @NewsRetrofit
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
  ): Retrofit =
    Retrofit
      .Builder()
      .baseUrl("https://newsapi.org/v2/")
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()

  @Provides
  @Singleton
  @GptRetrofit
  fun provideGptRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
  ): Retrofit =
    Retrofit
      .Builder()
      .baseUrl("https://api.openai.com/v1/")
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()

  @Provides
  @Singleton
  fun provideArticleApi(
    @NewsRetrofit retrofit: Retrofit,
  ): ArticleApi = retrofit.create(ArticleApi::class.java)

  @Provides
  @Singleton
  fun provideSourceApi(
    @NewsRetrofit retrofit: Retrofit,
  ): SourceApi = retrofit.create(SourceApi::class.java)

  @Provides
  @Singleton
  fun provideGptApi(
    @GptRetrofit retrofit: Retrofit,
  ): GptApi = retrofit.create(GptApi::class.java)
}
