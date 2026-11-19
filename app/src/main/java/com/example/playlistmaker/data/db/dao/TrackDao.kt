package com.example.playlistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.playlistmaker.data.db.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    /**
     * Сохраняет трек или обновляет уже существующий.
     *
     * Используется именно upsert, а не `OnConflictStrategy.REPLACE`: REPLACE удаляет
     * старую строку, а вместе с ней каскадно удалились бы связи с плейлистами.
     */
    @Upsert
    suspend fun upsertTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE trackId = :trackId LIMIT 1")
    suspend fun getTrackById(trackId: Long): TrackEntity?

    @Query("SELECT * FROM tracks WHERE trackName = :trackName AND artistName = :artistName LIMIT 1")
    fun getTrackByNameAndArtist(trackName: String, artistName: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE isFavorite = 1 ORDER BY rowid DESC")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>
}
