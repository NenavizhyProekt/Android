package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Трек, сохранённый в базе данных.
 *
 * В таблицу попадают только треки, которые пользователь добавил в «Избранное»
 * или в какой-либо плейлист: результаты поиска в базе не сохраняются.
 */
@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String,
    val isFavorite: Boolean = false,
)
