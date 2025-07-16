package com.yllielshani.twocentsdemo.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yllielshani.twocentsdemo.data.api.ApiService
import com.yllielshani.twocentsdemo.data.api.FakeItemApiService
import com.yllielshani.twocentsdemo.data.repository.PostRepository
import com.yllielshani.twocentsdemo.data.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindItemRepository(
        impl: PostRepositoryImpl
    ): PostRepository
}

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideItemApiService(): ApiService = FakeItemApiService()

//    @Provides @Singleton
//    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
//        Retrofit.Builder()
//            .baseUrl("https://api.twocents.money/")
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//
//    @Provides @Singleton
//    fun provideApiService(retrofit: Retrofit): ApiService =
//        retrofit.create(FakeItemApiService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient =
//        OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .build()
//
//
//    @Provides
//    @Singleton
//    fun provideMoshi(): Moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory())
//        .build()
}