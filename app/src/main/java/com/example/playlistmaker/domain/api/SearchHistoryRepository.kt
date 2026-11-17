package com.example.playlistmaker.domain.api

interface SearchHistoryRepository {
    fun getHistory(): List<String>

    fun addToHistory(word: String)

    fun clearHistory()
}
