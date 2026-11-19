package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.preferences.SearchHistoryPreferences
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val searchHistoryPreferences: SearchHistoryPreferences,
) : SearchHistoryRepository {

    override fun getHistory(): Flow<List<String>> {
        return searchHistoryPreferences.history
    }

    override suspend fun addToHistory(word: String) {
        searchHistoryPreferences.addEntry(word)
    }

    override suspend fun clearHistory() {
        searchHistoryPreferences.clearHistory()
    }
}
