package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class TracksRepositoryImpl(
    private val database: DatabaseMock,
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        return database.searchTracks(expression)
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        // Сохраняем трек, чтобы он отображался в плейлисте, не теряя статус «Избранное».
        val storedTrack = database.getTrackByNameAndArtist(track).firstOrNull()
        val trackToStore = storedTrack?.let { track.copy(favorite = it.favorite) } ?: track
        database.insertTrack(trackToStore)
        database.addTrackToPlaylist(trackToStore.id, playlistId)
    }

    override suspend fun removeTrackFromPlaylist(track: Track, playlistId: Long) {
        database.removeTrackFromPlaylist(track.id, playlistId)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }
}
