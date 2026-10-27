package com.example.playlistmaker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.theme.IconGray
import com.example.playlistmaker.ui.theme.LightGray
import com.example.playlistmaker.ui.theme.TextPrimary

@Composable
fun TrackListItem(
    track: Track,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LightGray),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = stringResource(R.string.track_cover),
                tint = IconGray,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = track.trackName,
                color = TextPrimary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = track.artistName,
                    color = IconGray,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )
                Text(
                    text = "•",
                    color = IconGray,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                )
                Text(
                    text = track.trackTime,
                    color = IconGray,
                    fontSize = 11.sp,
                    maxLines = 1,
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.arrow_forward),
            tint = IconGray,
            modifier = Modifier.size(24.dp),
        )
    }
}
