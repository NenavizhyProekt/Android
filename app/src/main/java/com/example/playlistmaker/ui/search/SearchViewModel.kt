package com.example.playlistmaker.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import com.example.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val trackRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _searchHistory = MutableStateFlow(searchHistoryRepository.getHistory())
    val searchHistory = _searchHistory.asStateFlow()

    private var searchJob: Job? = null

    /**
     * Вызывается при каждом изменении текста в поле поиска.
     * Поиск запускается автоматически через [SEARCH_DEBOUNCE_DELAY] после последнего символа.
     */
    fun onQueryChanged(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _searchScreenState.value = SearchState.Initial
            return
        }
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            performSearch(query)
        }
    }

    /**
     * Немедленный поиск: по нажатию на иконку поиска, по кнопке клавиатуры
     * или по клику на элемент истории поиска.
     */
    fun searchTracks(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) return
        searchJob = viewModelScope.launch {
            performSearch(query)
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _searchScreenState.value = SearchState.Initial
    }

    fun clearHistory() {
        searchHistoryRepository.clearHistory()
        _searchHistory.value = emptyList()
    }

    private suspend fun performSearch(query: String) {
        _searchScreenState.value = SearchState.Searching
        try {
            val result = trackRepository.searchTracks(query)
            _searchScreenState.value = if (result.isEmpty()) {
                SearchState.Fail(NO_RESULTS_MESSAGE)
            } else {
                SearchState.Success(result)
            }
            searchHistoryRepository.addToHistory(query)
            _searchHistory.value = searchHistoryRepository.getHistory()
        } catch (e: Exception) {
            _searchScreenState.value = SearchState.Fail("Ошибка при поиске: ${e.message}")
        }
    }

    companion object {
        const val NO_RESULTS_MESSAGE = "Ничего не найдено"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
