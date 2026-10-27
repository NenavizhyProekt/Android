package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.delay
import java.util.Locale

class TracksRepositoryImpl : TracksRepository {

    private val tracksDto = listOf(
        TrackDto(1, "Yesterday (Remastered 2009)", "The Beatles", 125000L),
        TrackDto(2, "Here Comes The Sun (Remastered 2019)", "The Beatles", 187000L),
        TrackDto(3, "No Reply", "The Beatles", 152000L),
        TrackDto(4, "Let It Be", "The Beatles", 243000L),
        TrackDto(5, "Girl", "The Beatles", 138000L),
        TrackDto(6, "Michelle", "The Beatles", 167000L),
        TrackDto(7, "Eleanor Rigby", "The Beatles", 198000L),
        TrackDto(8, "Come Together", "The Beatles", 259000L),
    )

    override suspend fun searchTracksContains(expression: String): List<Track> {
        // Имитация сетевого запроса, пока работа с сетью не изучена.
        delay(3000)

        return tracksDto
            .filter { track ->
                track.trackName.contains(expression, ignoreCase = true) ||
                    track.artistName.contains(expression, ignoreCase = true)
            }
            .map { trackDto ->
                Track(
                    id = trackDto.id,
                    trackName = trackDto.trackName,
                    artistName = trackDto.artistName,
                    trackTime = formatTrackTime(trackDto.trackTimeMillis),
                )
            }
    }

    private fun formatTrackTime(trackTimeMillis: Long): String {
        val totalSeconds = trackTimeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }
}
