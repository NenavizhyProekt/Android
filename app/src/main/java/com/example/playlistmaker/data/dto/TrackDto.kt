package com.example.playlistmaker.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Ответ iTunes Search API может не содержать часть полей, поэтому все они nullable.
 */
data class TrackDto(
    @SerializedName("trackId") val trackId: Long? = null,
    @SerializedName("trackName") val trackName: String? = null,
    @SerializedName("artistName") val artistName: String? = null,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long? = null,
    @SerializedName("artworkUrl100") val artworkUrl100: String? = null,
)
