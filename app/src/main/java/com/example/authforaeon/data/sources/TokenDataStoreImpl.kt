package com.example.authforaeon.data.sources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.authforaeon.domain.interfaces.TokenDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
): TokenDataStore {

    override val readToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_TOKEN] ?: ""
        }

    override suspend fun saveToken(token: String): Boolean {
        return try {
            context.dataStore.edit { pref ->
                pref[USER_TOKEN] = token
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Token")
        val USER_TOKEN = stringPreferencesKey("token")
    }
}