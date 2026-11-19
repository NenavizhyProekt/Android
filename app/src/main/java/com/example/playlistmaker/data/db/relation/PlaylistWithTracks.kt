package com.example.playlistmaker.data.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.playlistmaker.data.db.entity.PlaylistEntity
import com.example.playlistmaker.data.db.entity.PlaylistTrackEntity
import com.example.playlistmaker.data.db.entity.TrackEntity

/**
 * Плейлист вместе с его треками, полученный через промежуточную таблицу связи.
 */
data class PlaylistWithTracks(
    @Embedded
    val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "trackId",
        associateBy = Junction(
            value = PlaylistTrackEntity::class,
            parentColumn = "playlistId",
            entityColumn = "trackId",
        ),
    )
    val tracks: List<TrackEntity>,
)
