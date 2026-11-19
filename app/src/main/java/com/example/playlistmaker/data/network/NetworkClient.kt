package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.TracksSearchResponse

interface NetworkClient {
    suspend fun searchTracks(term: String): TracksSearchResponse
}
