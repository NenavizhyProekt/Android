package com.example.playlistmaker.data.di

import com.example.playlistmaker.data.network.TracksRepositoryImpl
import com.example.playlistmaker.domain.api.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<TracksRepository> {
        TracksRepositoryImpl()
    }
}
