package com.example.playlistmaker.data.db.mapper

import com.example.playlistmaker.data.db.entity.TrackEntity
import com.example.playlistmaker.data.db.relation.PlaylistWithTracks
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.domain.model.Track

fun TrackEntity.toDomain(): Track = Track(
    id = trackId,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    artworkUrl100 = artworkUrl100,
    favorite = isFavorite,
)

fun Track.toEntity(isFavorite: Boolean = favorite): TrackEntity = TrackEntity(
    trackId = id,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    artworkUrl100 = artworkUrl100,
    isFavorite = isFavorite,
)

fun PlaylistWithTracks.toDomain(): Playlist = Playlist(
    id = playlist.id,
    name = playlist.name,
    description = playlist.description,
    coverImageUri = playlist.coverImageUri,
    tracks = tracks.map { track -> track.toDomain() },
)
