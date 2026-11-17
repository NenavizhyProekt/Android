package com.example.playlistmaker.ui.navigation

/**
 * Маршруты навигации Playlist Maker.
 */
enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    SETTINGS("settings"),
    DETAILS("details/{trackJson}"),
    PLAYLISTS("playlists"),
    NEW_PLAYLIST("new_playlist"),
    FAVORITES("favorites");

    companion object {
        const val TRACK_JSON = "trackJson"
    }
}
