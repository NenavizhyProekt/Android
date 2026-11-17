package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.Locale

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: DatabaseMock,
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.searchTracks(expression)
        return response.results.map { trackDto -> trackDto.toDomain() }
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

private fun TrackDto.toDomain(): Track {
    val name = trackName.orEmpty()
    val artist = artistName.orEmpty()
    return Track(
        id = trackId ?: "$name $artist".hashCode().toLong(),
        trackName = name,
        artistName = artist,
        trackTime = formatTrackTime(trackTimeMillis),
        artworkUrl100 = artworkUrl100.orEmpty(),
    )
}

private fun formatTrackTime(trackTimeMillis: Long?): String {
    val totalSeconds = (trackTimeMillis ?: 0L) / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}
