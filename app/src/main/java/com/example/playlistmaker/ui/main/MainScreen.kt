package com.example.playlistmaker.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.theme.IconGray
import com.example.playlistmaker.ui.theme.PlaylistBlue
import com.example.playlistmaker.ui.theme.PlaylistMakerTheme
import com.example.playlistmaker.ui.theme.TextPrimary
import com.example.playlistmaker.ui.theme.White

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaylistBlue),
    ) {
        Text(
            text = stringResource(R.string.main_title),
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                MenuItem(
                    icon = Icons.Filled.Search,
                    title = stringResource(R.string.search),
                    onClick = onSearchClick,
                )
                MenuItem(
                    icon = Icons.AutoMirrored.Filled.PlaylistPlay,
                    title = stringResource(R.string.playlists),
                    onClick = onPlaylistsClick,
                )
                MenuItem(
                    icon = Icons.Filled.FavoriteBorder,
                    title = stringResource(R.string.favorites),
                    onClick = onFavoritesClick,
                )
                MenuItem(
                    icon = Icons.Filled.Settings,
                    title = stringResource(R.string.settings),
                    onClick = onSettingsClick,
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextPrimary,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = TextPrimary,
            fontSize = 22.sp,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = IconGray,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    PlaylistMakerTheme {
        MainScreen(
            onSearchClick = {},
            onPlaylistsClick = {},
            onFavoritesClick = {},
            onSettingsClick = {},
        )
    }
}
