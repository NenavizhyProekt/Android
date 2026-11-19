package com.example.playlistmaker.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val tracksRepository: TracksRepository,
    playlistsRepository: PlaylistsRepository,
) : ViewModel() {

    private val _track = MutableStateFlow<Track?>(null)
    val track: StateFlow<Track?> = _track.asStateFlow()

    val playlists: StateFlow<List<Playlist>> = playlistsRepository.getAllPlaylists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = emptyList(),
        )

    /**
     * Подтягивает актуальное состояние трека из хранилища: например, статус «Избранное».
     */
    fun loadTrack(track: Track) {
        viewModelScope.launch {
            val storedTrack = tracksRepository.getTrackByNameAndArtist(track).firstOrNull()
            _track.value = storedTrack ?: track
        }
    }

    fun toggleFavorite() {
        val currentTrack = _track.value ?: return
        val newFavoriteStatus = !currentTrack.favorite
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(currentTrack, newFavoriteStatus)
            _track.value = currentTrack.copy(favorite = newFavoriteStatus)
        }
    }

    fun addToPlaylist(playlistId: Long) {
        val currentTrack = _track.value ?: return
        viewModelScope.launch {
            tracksRepository.insertTrackToPlaylist(currentTrack, playlistId)
        }
    }

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
