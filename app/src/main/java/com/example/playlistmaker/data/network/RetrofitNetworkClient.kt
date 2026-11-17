package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.TracksSearchResponse

class RetrofitNetworkClient(
    private val api: ITunesApi,
) : NetworkClient {

    override suspend fun searchTracks(term: String): TracksSearchResponse {
        return api.searchTracks(
            term = term,
            entity = ENTITY_SONG,
            limit = SEARCH_LIMIT,
        )
    }

    private companion object {
        const val ENTITY_SONG = "song"
        const val SEARCH_LIMIT = 50
    }
}
