package com.sudansh.music.presentation.playlist

import android.net.Uri

sealed class PlaylistItem {
    class Heading(val title: String) : PlaylistItem()
    class Media(val media: Song) : PlaylistItem()

    data class Song(
        val mediaId: String,
        val artwork: Uri?,
        val title: String,
        val artist: String
    )
}