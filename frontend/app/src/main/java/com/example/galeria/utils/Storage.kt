package com.example.galeria.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class Storage {
    companion object {
        suspend fun saveHash(newHash: Int, context: Context, mediaType: String) {
            val hashKey = intPreferencesKey(mediaType + "_hash")

            context.dataStore.edit { settings ->
                settings[hashKey] = newHash
            }
        }

        suspend fun retrieveHash(context: Context, mediaType: MediaType): Int {
            val hashKey = intPreferencesKey(mediaType.name + "_hash")
            val hashFlow: Flow<Int> = context.dataStore.data
                .map { preferences ->
                    preferences[hashKey] ?: 0
                }.cancellable()

            return hashFlow.first()
        }

        // TODO: enum
        fun saveFilterPreference() {

        }

        fun saveMediaLocation() {

        }
    }
}