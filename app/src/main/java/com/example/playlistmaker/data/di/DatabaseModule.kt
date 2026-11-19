package com.example.playlistmaker.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.playlistmaker.data.db.AppDatabase
import com.example.playlistmaker.data.db.dao.PlaylistDao
import com.example.playlistmaker.data.db.dao.TrackDao
import com.example.playlistmaker.data.preferences.SearchHistoryPreferences
import com.example.playlistmaker.data.preferences.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME = "playlist_maker.db"

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    single<TrackDao> { get<AppDatabase>().trackDao() }

    single<PlaylistDao> { get<AppDatabase>().playlistDao() }

    single<DataStore<Preferences>> { androidContext().dataStore }

    single { SearchHistoryPreferences(get()) }
}
