package com.example.playlistmaker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.common.ScreenHeader
import com.example.playlistmaker.ui.theme.IconGray
import com.example.playlistmaker.ui.theme.LightGray
import com.example.playlistmaker.ui.theme.PlaylistBlue
import com.example.playlistmaker.ui.theme.TextPrimary
import com.example.playlistmaker.ui.theme.White

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    navigateToDetailScreen: (Track) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    val history by searchViewModel.searchHistory.collectAsState()
    val focusManager = LocalFocusManager.current
    var query by rememberSaveable { mutableStateOf("") }

    val onSearchClick = {
        if (query.isNotBlank()) {
            searchViewModel.searchTracks(query)
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PlaylistBlue),
    ) {
        ScreenHeader(
            title = stringResource(R.string.title_search),
            onBackClick = onBackClick,
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .imePadding(),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                SearchTextField(
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                        searchViewModel.onQueryChanged(newQuery)
                    },
                    onSearchClick = onSearchClick,
                    onClearClick = {
                        query = ""
                        searchViewModel.clearSearch()
                        focusManager.clearFocus()
                    },
                )

                if (query.isEmpty() && history.isNotEmpty()) {
                    SearchHistory(
                        history = history,
                        onHistoryClick = { historyQuery ->
                            query = historyQuery
                            searchViewModel.searchTracks(historyQuery)
                            focusManager.clearFocus()
                        },
                        onClearHistoryClick = { searchViewModel.clearHistory() },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                    )
                } else {
                    SearchResults(
                        screenState = screenState,
                        onTrackClick = navigateToDetailScreen,
                        onRefreshClick = { searchViewModel.retry() },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        singleLine = true,
        textStyle = TextStyle(
            color = TextPrimary,
            fontSize = 16.sp,
        ),
        cursorBrush = SolidColor(PlaylistBlue),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchClick() }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search),
                        tint = IconGray,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_placeholder),
                            color = IconGray,
                            fontSize = 16.sp,
                        )
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = onClearClick,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_query),
                            tint = IconGray,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun SearchHistory(
    history: List<String>,
    onHistoryClick: (String) -> Unit,
    onClearHistoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.search_history),
                modifier = Modifier.weight(1f),
                color = TextPrimary,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium,
            )
            TextButton(onClick = onClearHistoryClick) {
                Text(
                    text = stringResource(R.string.clear_history),
                    color = PlaylistBlue,
                    fontSize = 14.sp,
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            items(items = history) { historyItem ->
                HistoryItem(
                    query = historyItem,
                    onClick = { onHistoryClick(historyItem) },
                )
            }
        }
    }
}

@Composable
private fun HistoryItem(
    query: String,
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
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = IconGray,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = query,
            modifier = Modifier.weight(1f),
            color = TextPrimary,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = IconGray,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun SearchResults(
    screenState: SearchState,
    onTrackClick: (Track) -> Unit,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (screenState) {
        SearchState.Initial -> Unit

        SearchState.Searching -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = PlaylistBlue)
            }
        }

        is SearchState.Success -> {
            val tracks = screenState.foundList
            if (tracks.isEmpty()) {
                PlaceholderState(
                    icon = Icons.Filled.SearchOff,
                    title = stringResource(R.string.no_tracks_found),
                    modifier = modifier,
                )
            } else {
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = tracks, key = { it.id }) { track ->
                        TrackListItem(
                            track = track,
                            onClick = { onTrackClick(track) },
                        )
                    }
                }
            }
        }

        SearchState.Empty -> {
            PlaceholderState(
                icon = Icons.Filled.SearchOff,
                title = stringResource(R.string.no_tracks_found),
                modifier = modifier,
            )
        }

        is SearchState.Fail -> {
            PlaceholderState(
                icon = Icons.Filled.CloudOff,
                title = stringResource(R.string.search_error),
                subtitle = stringResource(R.string.check_connection),
                onRefreshClick = onRefreshClick,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun PlaceholderState(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onRefreshClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = IconGray,
            modifier = Modifier.size(120.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            color = TextPrimary,
            fontSize = 19.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                color = IconGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
        }
        if (onRefreshClick != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRefreshClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlaylistBlue,
                    contentColor = White,
                ),
            ) {
                Text(
                    text = stringResource(R.string.refresh),
                    fontSize = 14.sp,
                )
            }
        }
    }
}
