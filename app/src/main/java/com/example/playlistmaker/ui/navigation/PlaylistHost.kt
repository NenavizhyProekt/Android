package com.example.playlistmaker.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.main.MainScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.settings.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
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
            SearchScreen(onBackClick = { navigateBack(navController) })
        }

        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navigateBack(navController) })
        }
    }
}

private fun navigateTo(navController: NavHostController, screen: Screen) {
    navController.navigate(screen.route) {
        launchSingleTop = true
    }
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
