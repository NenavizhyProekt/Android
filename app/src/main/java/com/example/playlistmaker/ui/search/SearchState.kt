package com.example.playlistmaker.ui.search

import com.example.playlistmaker.domain.model.Track

sealed class SearchState {
    data object Initial : SearchState()
    data object Searching : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()

    /** Поиск завершился успешно, но ни одного трека не найдено. */
    data object Empty : SearchState()

    /** Запрос завершился ошибкой: нет сети или сервер вернул код ошибки. */
    data class Fail(val error: String) : SearchState()
}
