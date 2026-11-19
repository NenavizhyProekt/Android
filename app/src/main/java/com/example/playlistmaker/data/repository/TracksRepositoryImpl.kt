package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.dao.PlaylistDao
import com.example.playlistmaker.data.db.dao.TrackDao
import com.example.playlistmaker.data.db.entity.PlaylistTrackEntity
import com.example.playlistmaker.data.db.mapper.toDomain
import com.example.playlistmaker.data.db.mapper.toEntity
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackDao: TrackDao,
    private val playlistDao: PlaylistDao,
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.searchTracks(expression)
        return response.results.map { trackDto -> trackDto.toDomain() }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return trackDao.getTrackByNameAndArtist(track.trackName, track.artistName)
            .map { entity -> entity?.toDomain() }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavoriteTracks()
            .map { entities -> entities.map { entity -> entity.toDomain() } }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        // Сохраняем трек в базу, не теряя статус «Избранное», если он уже был сохранён.
        val storedTrack = trackDao.getTrackById(track.id)
        val isFavorite = storedTrack?.isFavorite ?: track.favorite
        trackDao.upsertTrack(track.toEntity(isFavorite))
        playlistDao.addTrackToPlaylist(
            PlaylistTrackEntity(playlistId = playlistId, trackId = track.id),
        )
    }

    override suspend fun removeTrackFromPlaylist(track: Track, playlistId: Long) {
        playlistDao.removeTrackFromPlaylist(playlistId = playlistId, trackId = track.id)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        trackDao.upsertTrack(track.toEntity(isFavorite))
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
