package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun removeTrackFromPlaylist(track: Track, playlistId: Long)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
}
