package com.example.playlistmaker.domain.api

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getHistory(): Flow<List<String>>

    suspend fun addToHistory(word: String)

    suspend fun clearHistory()
}
