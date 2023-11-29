package com.example.authforaeon.di

import com.example.authforaeon.data.repository.RepositoryImpl
import com.example.authforaeon.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(
        repository: RepositoryImpl
    ): Repository


}