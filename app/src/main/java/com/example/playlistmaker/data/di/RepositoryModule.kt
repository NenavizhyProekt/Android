package com.example.playlistmaker.data.di

import com.example.playlistmaker.data.DatabaseMock
import com.example.playlistmaker.data.network.TracksRepositoryImpl
import com.example.playlistmaker.data.repository.PlaylistsRepositoryImpl
import com.example.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.example.playlistmaker.domain.api.PlaylistsRepository
import com.example.playlistmaker.domain.api.SearchHistoryRepository
import com.example.playlistmaker.domain.api.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<DatabaseMock> { DatabaseMock() }

    factory<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    factory<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get())
    }

    factory<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }
}
