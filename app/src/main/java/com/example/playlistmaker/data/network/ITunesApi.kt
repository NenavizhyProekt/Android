package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.TracksSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {

    @GET("search")
    suspend fun searchTracks(
        @Query("term") term: String,
        @Query("entity") entity: String,
        @Query("limit") limit: Int,
    ): TracksSearchResponse
}
