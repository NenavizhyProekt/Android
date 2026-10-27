package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.model.Track

interface TracksRepository {
    suspend fun searchTracksContains(expression: String): List<Track>
}
