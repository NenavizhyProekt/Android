package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val database: DatabaseMock,
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverImageUri: String?,
    ) {
        database.addNewPlaylist(name, description, coverImageUri)
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(playlistId = id)
    }
}
