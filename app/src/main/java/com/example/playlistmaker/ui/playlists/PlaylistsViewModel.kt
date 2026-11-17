package com.example.playlistmaker.ui.playlists

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistsViewModel(
    playlistsRepository: PlaylistsRepository,
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()
}
