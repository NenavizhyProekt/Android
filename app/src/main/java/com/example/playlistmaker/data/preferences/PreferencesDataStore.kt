package com.example.playlistmaker.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Единственный экземпляр DataStore на всё приложение.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "playlist_maker_preferences",
)
