package com.example.playlistmaker.data.di

import com.example.playlistmaker.data.network.ITunesApi
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.repository.PlaylistsRepositoryImpl
import com.example.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import com.example.playlistmaker.domain.api.TracksRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ITUNES_BASE_URL = "https://itunes.apple.com/"

val repositoryModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ITUNES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ITunesApi> {
        get<Retrofit>().create(ITunesApi::class.java)
    }

    factory<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    factory<TracksRepository> {
        TracksRepositoryImpl(get(), get(), get())
    }

    factory<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get())
    }

    factory<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }
}
