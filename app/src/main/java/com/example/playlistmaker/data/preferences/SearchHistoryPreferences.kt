package com.example.playlistmaker.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Хранит историю поиска в DataStore.
 *
 * Правила: не более [MAX_ENTRIES] запросов, без дубликатов, самый свежий запрос — первым.
 */
class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
) {

    val history: Flow<List<String>> = dataStore.data
        .catch { throwable ->
            if (throwable is IOException) {
                emit(emptyPreferences())
            } else {
                throw throwable
            }
        }
        .map { preferences -> parse(preferences[HISTORY_KEY]) }

    suspend fun addEntry(word: String) {
        val entry = word.trim()
        if (entry.isEmpty()) return

        dataStore.edit { preferences ->
            val history = parse(preferences[HISTORY_KEY])
                .filterNot { it == entry }
                .toMutableList()
            history.add(0, entry)
            preferences[HISTORY_KEY] = history
                .take(MAX_ENTRIES)
                .joinToString(SEPARATOR)
        }
    }

    suspend fun clearHistory() {
        dataStore.edit { preferences ->
            preferences.remove(HISTORY_KEY)
        }
    }

    private fun parse(value: String?): List<String> =
        value.orEmpty()
            .split(SEPARATOR)
            .filter { it.isNotEmpty() }

    private companion object {
        const val MAX_ENTRIES = 10

        /**
         * Разделитель, который не может встретиться в поисковом запросе.
         * Обычная запятая не подходит: она допустима в названии трека.
         */
        const val SEPARATOR = "\u0001"

        val HISTORY_KEY = stringPreferencesKey("search_history")
    }
}
