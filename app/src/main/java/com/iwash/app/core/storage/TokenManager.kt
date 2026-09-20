package com.iwash.app.core.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "iwash_prefs")

/**
 * Equivalent of the Flutter app's SharedPreferences token handling
 * (see: main.dart / register_repo.dart).
 */
class TokenManager(private val context: Context) {

    private val tokenKey = stringPreferencesKey("token")

    /** Cached token, read once at app startup (mirrors the global `token` var in main.dart). */
    var cachedToken: String? = null
        private set

    fun loadCachedTokenBlocking() {
        cachedToken = runBlocking { tokenFlow().first() }
    }

    fun tokenFlow(): Flow<String?> =
        context.dataStore.data.map { it[tokenKey] }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[tokenKey] = token }
        cachedToken = token
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(tokenKey) }
        cachedToken = null
    }
}
