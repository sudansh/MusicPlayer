package com.sudansh.music.datasource

import com.google.android.exoplayer2.MediaItem
import com.sudansh.music.model.Lyrics

interface MediaDataSource {
    fun getLyrics(mediaId: String, title: String): Lyrics
    fun getMedias(): List<MediaItem>
}
