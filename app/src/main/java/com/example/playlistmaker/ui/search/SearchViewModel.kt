package com.example.playlistmaker.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val trackRepository: TracksRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    fun searchTracks(query: String) {
        viewModelScope.launch {
            _searchScreenState.value = SearchState.Searching
            try {
                val result = trackRepository.searchTracksContains(query)
                if (result.isNotEmpty()) {
                    _searchScreenState.value = SearchState.Success(result)
                } else {
                    _searchScreenState.value = SearchState.Fail(NO_RESULTS_MESSAGE)
                }
            } catch (e: Exception) {
                _searchScreenState.value = SearchState.Fail("Ошибка при поиске: ${e.message}")
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.value = SearchState.Initial
    }

    companion object {
        const val NO_RESULTS_MESSAGE = "Ничего не найдено"
    }
}
