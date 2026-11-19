package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.db.dao.PlaylistDao
import com.example.playlistmaker.data.db.entity.PlaylistEntity
import com.example.playlistmaker.data.db.mapper.toDomain
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistDao,
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistDao.getPlaylistWithTracks(playlistId)
            .map { playlistWithTracks -> playlistWithTracks?.toDomain() }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylistsWithTracks()
            .map { playlists -> playlists.map { playlist -> playlist.toDomain() } }
    }

    override suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverImageUri: String?,
    ) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                coverImageUri = coverImageUri,
            ),
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deletePlaylistById(playlistId = id)
    }
}
