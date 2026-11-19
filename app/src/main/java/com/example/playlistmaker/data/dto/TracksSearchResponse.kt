package com.example.playlistmaker.data.dto

import com.google.gson.annotations.SerializedName

data class TracksSearchResponse(
    @SerializedName("resultCount") val resultCount: Int = 0,
    @SerializedName("results") val results: List<TrackDto> = emptyList(),
)
