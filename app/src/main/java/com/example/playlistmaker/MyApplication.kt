package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.data.di.databaseModule
import com.example.playlistmaker.data.di.repositoryModule
import com.example.playlistmaker.ui.details.DetailsViewModel
import com.example.playlistmaker.ui.newplaylist.NewPlaylistViewModel
import com.example.playlistmaker.ui.playlist.PlaylistViewModel
import com.example.playlistmaker.ui.playlists.PlaylistsViewModel
import com.example.playlistmaker.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                databaseModule,
                repositoryModule,
                module {
                    viewModel { SearchViewModel(get(), get()) }
                    viewModel { PlaylistsViewModel(get()) }
                    viewModel { NewPlaylistViewModel(get()) }
                    viewModel { params -> PlaylistViewModel(get(), params.get()) }
                    viewModel { DetailsViewModel(get(), get()) }
                },
            )
        }
    }
}
