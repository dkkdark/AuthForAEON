package com.example.authforaeon.di

import com.example.authforaeon.data.sources.TokenDataStoreImpl
import com.example.authforaeon.domain.interfaces.TokenDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun bindSaveUserToken(userSave: TokenDataStoreImpl):
            TokenDataStore
}