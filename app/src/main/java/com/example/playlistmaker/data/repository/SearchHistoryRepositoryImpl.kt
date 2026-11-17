package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.domain.api.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val database: DatabaseMock,
) : SearchHistoryRepository {

    override fun getHistory(): List<String> {
        return database.getHistory()
    }

    override fun addToHistory(word: String) {
        if (word.isBlank()) return
        database.addToHistory(word.trim())
    }

    override fun clearHistory() {
        database.clearHistory()
    }
}
