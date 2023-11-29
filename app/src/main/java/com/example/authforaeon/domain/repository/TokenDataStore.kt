package com.example.authforaeon.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    val readToken: Flow<String>
    suspend fun saveToken(token: String): Boolean
}