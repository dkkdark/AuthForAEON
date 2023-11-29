package com.example.authforaeon.domain.interfaces

import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    val readToken: Flow<String>
    suspend fun saveToken(token: String): Boolean
}