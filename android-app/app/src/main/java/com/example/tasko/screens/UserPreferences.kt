package com.example.tasko.screens

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {
    private val dataStore = context.dataStore

    val usernameFlow: Flow<String?> = dataStore.data.map { it[USERNAME_KEY] }
    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val PASSWORD_KEY = stringPreferencesKey("password")
    }

    val userFlow: Flow<Pair<String?, String?>> = context.dataStore.data
        .map { prefs ->
            val username = prefs[USERNAME_KEY]
            val password = prefs[PASSWORD_KEY]
            username to password
        }

    suspend fun saveUser(username: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}