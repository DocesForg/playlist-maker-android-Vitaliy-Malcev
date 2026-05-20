package com.example.project.data.network

import android.content.Context
import android.net.Uri
import com.example.project.data.database.AppDatabase
import com.example.project.data.database.mapper.PlaylistMapper.toEntity
import com.example.project.data.database.mapper.PlaylistMapper.toPlaylist
import com.example.project.data.database.mapper.TrackMapper.toEntity
import com.example.project.data.database.mapper.TrackMapper.toTrack
import com.example.project.domain.Playlist
import com.example.project.domain.PlaylistsRepository
import com.example.project.domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlaylistsRepositoryImpl(
    database: AppDatabase,
    private val context: Context
) : PlaylistsRepository {
    private val playlistsDao = database.playlistsDao()
    private val tracksDao = database.tracksDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsDao.getPlaylistById(playlistId)
            .combine(tracksDao.getTracksByPlaylistId(playlistId)) { playlistEntity, trackEntities ->
                playlistEntity?.let { entity ->
                    entity.toPlaylist(
                        trackEntities.map { trackEntity -> trackEntity.toTrack() }
                    )
                }
            }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return combine(
            playlistsDao.getAllPlaylists(),
            tracksDao.getAllTracks()
        ) { playlistEntities, trackEntities ->

            playlistEntities.map { playlist ->

                val tracks = trackEntities
                    .filter { it.playlistId == playlist.id }
                    .map { it.toTrack() }

                playlist.toPlaylist(tracks)
            }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        val savedCoverImageUri = saveCoverImageToInternalStorage(coverImageUri)
        val playlist = Playlist(
            name = name,
            description = description,
            coverImageUri = savedCoverImageUri,
            tracks = emptyList()
        )
        playlistsDao.insertPlaylist(
            playlist.toEntity()
        )
    }

    private fun saveCoverImageToInternalStorage(sourceUri: String?): String? {
        if (sourceUri.isNullOrBlank()) return null

        return runCatching {
            val coversDir = java.io.File(context.filesDir, "playlist_covers").apply { mkdirs() }
            val coverFile = java.io.File(coversDir, "cover_${System.currentTimeMillis()}.jpg")

            context.contentResolver.openInputStream(Uri.parse(sourceUri))?.use { inputStream ->
                coverFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            } ?: return sourceUri

            Uri.fromFile(coverFile).toString()
        }.getOrDefault(sourceUri)
    }

    override suspend fun removeTrackFromPlaylist(track: Track) {
        if (track.favorite) {
            tracksDao.insertTrack(
                track.copy(playlistId = 0).toEntity()
            )
        } else {
            tracksDao.removeTrackFromPlaylist(track.id, track.playlistId)

            val newTrack = track.copy(playlistId = 0)
            tracksDao.insertTrack(newTrack.toEntity())
            tracksDao.deleteTrack(track.id)
        }
    }
}