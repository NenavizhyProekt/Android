package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.playlistmaker.ui.main.MainScreen
import com.example.playlistmaker.ui.theme.PlaylistMakerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            ),
        )
        setContent {
            PlaylistMakerTheme {
                MainScreen(
                    onSearchClick = {
                        startActivity(Intent(this, SearchActivity::class.java))
                    },
                    onPlaylistsClick = { showButtonPressedToast(getString(R.string.playlists)) },
                    onFavoritesClick = { showButtonPressedToast(getString(R.string.favorites)) },
                    onSettingsClick = {
                        startActivity(Intent(this, SettingsActivity::class.java))
                    },
                )
            }
        }
    }

    private fun showButtonPressedToast(buttonName: String) {
        Toast.makeText(
            this,
            getString(R.string.button_pressed, buttonName),
            Toast.LENGTH_SHORT,
        ).show()
    }
}
