package com.example.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.example.playlistmaker.ui.navigation.PlaylistHost
import com.example.playlistmaker.ui.search.SearchViewModel
import com.example.playlistmaker.ui.theme.PlaylistMakerTheme
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

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
                val navController = rememberNavController()
                PlaylistHost(
                    navController = navController,
                    searchViewModel = searchViewModel,
                    gson = remember { Gson() },
                )
            }
        }
    }
}
