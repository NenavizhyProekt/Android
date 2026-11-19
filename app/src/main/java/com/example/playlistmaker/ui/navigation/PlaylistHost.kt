package com.example.playlistmaker.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.details.DetailsScreen
import com.example.playlistmaker.ui.details.DetailsViewModel
import com.example.playlistmaker.ui.favorites.FavoritesScreen
import com.example.playlistmaker.ui.favorites.FavoritesViewModel
import com.example.playlistmaker.ui.main.MainScreen
import com.example.playlistmaker.ui.newplaylist.NewPlaylistScreen
import com.example.playlistmaker.ui.newplaylist.NewPlaylistViewModel
import com.example.playlistmaker.ui.playlist.PlaylistScreen
import com.example.playlistmaker.ui.playlist.PlaylistViewModel
import com.example.playlistmaker.ui.playlists.PlaylistsScreen
import com.example.playlistmaker.ui.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.search.SearchViewModel
import com.example.playlistmaker.ui.settings.SettingsScreen
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlaylistHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    gson: Gson,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
        modifier = modifier,
    ) {
        composable(route = Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navigateTo(navController, Screen.SEARCH) },
                onPlaylistsClick = { navigateTo(navController, Screen.PLAYLISTS) },
                onFavoritesClick = { navigateTo(navController, Screen.FAVORITES) },
                onSettingsClick = { navigateTo(navController, Screen.SETTINGS) },
            )
        }

        composable(route = Screen.SEARCH.route) {
            SearchScreen(
                searchViewModel = searchViewModel,
                navigateToDetailScreen = { track ->
                    navigateToTrackDetails(navController, gson, track)
                },
                onBackClick = { navigateBack(navController) },
            )
        }

        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navigateBack(navController) })
        }

        composable(route = Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                playlistsViewModel = koinViewModel<PlaylistsViewModel>(),
                addNewPlaylist = { navigateTo(navController, Screen.NEW_PLAYLIST) },
                navigateToPlaylist = { playlistId ->
                    navController.navigate("playlist/$playlistId")
                },
                onBackClick = { navigateBack(navController) },
            )
        }

        composable(
            route = Screen.PLAYLIST.route,
            arguments = listOf(
                navArgument(Screen.PLAYLIST_ID) { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong(Screen.PLAYLIST_ID) ?: 0L
            PlaylistScreen(
                viewModel = koinViewModel<PlaylistViewModel> {
                    parametersOf(playlistId)
                },
                onTrackClick = { track ->
                    navigateToTrackDetails(navController, gson, track)
                },
                navigateBack = { navigateBack(navController) },
            )
        }

        composable(route = Screen.NEW_PLAYLIST.route) {
            NewPlaylistScreen(
                viewModel = koinViewModel<NewPlaylistViewModel>(),
                navigateBack = { navigateBack(navController) },
            )
        }

        composable(route = Screen.FAVORITES.route) {
            FavoritesScreen(
                viewModel = koinViewModel<FavoritesViewModel>(),
                onTrackClick = { track ->
                    navigateToTrackDetails(navController, gson, track)
                },
                onBackClick = { navigateBack(navController) },
            )
        }

        composable(
            route = Screen.DETAILS.route,
            arguments = listOf(
                navArgument(Screen.TRACK_JSON) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val trackJson = backStackEntry.arguments?.getString(Screen.TRACK_JSON)
            val track = trackJson?.let { json ->
                runCatching { gson.fromJson(json, Track::class.java) }.getOrNull()
            }
            if (track != null) {
                DetailsScreen(
                    track = track,
                    viewModel = koinViewModel<DetailsViewModel>(),
                    onBackClick = { navigateBack(navController) },
                )
            } else {
                LaunchedEffect(Unit) {
                    navigateBack(navController)
                }
            }
        }
    }
}

private fun navigateTo(navController: NavHostController, screen: Screen) {
    navController.navigate(screen.route) {
        launchSingleTop = true
    }
}

private fun navigateToTrackDetails(
    navController: NavHostController,
    gson: Gson,
    track: Track,
) {
    val trackJson = Uri.encode(gson.toJson(track))
    navController.navigate("details/$trackJson")
}

private fun navigateBack(navController: NavHostController) {
    navController.navigateUp()
}
