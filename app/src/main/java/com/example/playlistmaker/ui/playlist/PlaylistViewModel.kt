package com.example.playlistmaker.ui.playlist

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistViewModel(
    playlistsRepository: PlaylistsRepository,
    playlistId: Long,
) : ViewModel() {

    val playlist: Flow<Playlist?> = playlistsRepository.getPlaylist(playlistId)
}
