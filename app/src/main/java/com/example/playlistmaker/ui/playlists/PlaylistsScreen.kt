package com.example.playlistmaker.ui.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.common.ScreenHeader
import com.example.playlistmaker.ui.theme.IconGray
import com.example.playlistmaker.ui.theme.PlaylistBlue
import com.example.playlistmaker.ui.theme.TextPrimary
import com.example.playlistmaker.ui.theme.White

@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val playlists by playlistsViewModel.playlists.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlaylistBlue),
    ) {
        ScreenHeader(
            title = stringResource(R.string.title_playlists),
            onBackClick = onBackClick,
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (playlists.isEmpty()) {
                    Text(
                        text = stringResource(R.string.empty_playlists),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        color = TextPrimary,
                        fontSize = 19.sp,
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp),
                    ) {
                        items(items = playlists, key = { it.id }) { playlist ->
                            PlaylistListItem(
                                playlist = playlist,
                                onClick = { navigateToPlaylist(playlist.id) },
                            )
                        }
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    onClick = addNewPlaylist,
                    containerColor = PlaylistBlue,
                    contentColor = White,
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add_playlist),
                    )
                }
            }
        }
    }
}
