package com.example.playlistmaker.ui.navigation

/**
 * Экраны приложения Playlist Maker.
 */
enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    SETTINGS("settings"),
    DETAILS("details/{trackJson}");

    companion object {
        const val TRACK_JSON = "trackJson"
    }
}
