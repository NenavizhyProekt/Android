package com.example.playlistmaker.ui.navigation

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.details.DetailsScreen
import com.example.playlistmaker.ui.main.MainScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.search.SearchViewModel
import com.example.playlistmaker.ui.settings.SettingsScreen
import com.google.gson.Gson

@Composable
fun PlaylistHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    gson: Gson,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
        modifier = modifier,
    ) {
        composable(route = Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navigateTo(navController, Screen.SEARCH) },
                onPlaylistsClick = {
                    showButtonPressedToast(context, context.getString(R.string.playlists))
                },
                onFavoritesClick = {
                    showButtonPressedToast(context, context.getString(R.string.favorites))
                },
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

private fun showButtonPressedToast(context: Context, buttonName: String) {
    Toast.makeText(
        context,
        context.getString(R.string.button_pressed, buttonName),
        Toast.LENGTH_SHORT,
    ).show()
}
