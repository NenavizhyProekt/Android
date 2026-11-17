package com.example.playlistmaker.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.common.ScreenHeader
import com.example.playlistmaker.ui.theme.PlaylistBlue
import com.example.playlistmaker.ui.theme.TextPrimary
import com.example.playlistmaker.ui.theme.White

/**
 * Заготовка экрана «Избранное». Список избранных треков появится в следующих спринтах.
 */
@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlaylistBlue),
    ) {
        ScreenHeader(
            title = stringResource(R.string.title_favorites),
            onBackClick = onBackClick,
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.empty_favorites),
                    color = TextPrimary,
                    fontSize = 19.sp,
                )
            }
        }
    }
}
