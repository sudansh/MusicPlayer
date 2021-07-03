package com.sudansh.music.presentation.playlist

import androidx.lifecycle.*
import com.sudansh.music.player.MusicService

class PlayListViewModel(musicService: MusicService) : ViewModel() {

    private val _mediaItems = MutableLiveData(musicService.mediaItems)

    val mediaItems: LiveData<List<PlaylistItem>> = _mediaItems.distinctUntilChanged().map {
        listOf(
            PlaylistItem.Heading("Up Next")
        ).plus(it.map {
            PlaylistItem.Media(
                PlaylistItem.Song(
                    artist = it.mediaMetadata.artist.toString(),
                    artwork = it.mediaMetadata.artworkUri,
                    title = it.mediaMetadata.title.toString(),
                    mediaId = it.mediaId
                )
            )
        })
    }
}