package com.example.galeria.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Storage {
    companion object {
        val HASH = longPreferencesKey("hash")

        suspend fun saveHash(newHash: Long, context: Context) {
            context.dataStore.edit { settings ->
                settings[HASH] = newHash
            }
        }

        suspend fun retrieveHash(context: Context): Long {
            val hashFlow: Flow<Long> = context.dataStore.data
                .map { preferences ->
                    preferences[HASH] ?: -1
                }

            var hash: Long = -1
            hashFlow.collect {
                hash = it
            }

            return hash
        }

        // TODO: enum
        fun saveFilterPreference() {

        }

        fun saveMediaLocation() {

        }
    }
}