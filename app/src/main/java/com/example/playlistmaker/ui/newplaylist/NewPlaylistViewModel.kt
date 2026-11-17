package com.example.playlistmaker.ui.newplaylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.api.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
) : ViewModel() {

    fun createNewPlaylist(name: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(
                name = name.trim(),
                description = description.trim(),
            )
        }
    }
}
