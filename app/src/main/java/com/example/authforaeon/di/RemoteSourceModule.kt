package com.example.authforaeon.di

import com.example.authforaeon.data.sources.RetrofitSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteSourceModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://easypay.world/api-test/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String) : RetrofitSource = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(RetrofitSource::class.java)
}