package com.sudansh.music.player

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.sudansh.music.datasource.MediaDataSource
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MusicService(
    private val context: Context,
    mediaRepo: MediaDataSource
) {
    private val exoPlayer by lazy { SimpleExoPlayer.Builder(context).build() }
    var mediaItems = mediaRepo.getMedias()
    private val currentMediaItem: BehaviorSubject<MediaItem> = BehaviorSubject.create()
    private val audioSessionIdSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    private val nextMediaItem: Observable<MediaItem> = currentMediaItem.map {
        val indexOfCurrent = mediaItems.indexOf(it)
        try {
            mediaItems[indexOfCurrent + 1]
        } catch (e: Exception) {
            MediaItem.Builder().build()
        }
    }

    fun observePlayingMedia(): Flowable<MediaItem> = currentMediaItem
        .doOnSubscribe { play() }
        .doOnDispose { destroy() }
        .toFlowable(BackpressureStrategy.LATEST)
        .share()

    fun observeNextMedia() = nextMediaItem
    fun observeAudioSessionId() = audioSessionIdSubject

    private val playerListener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            if (mediaItem != null) {
                currentMediaItem.onNext(mediaItem)
            }
        }
    }

    init {
        audioSessionIdSubject.onNext(exoPlayer.audioSessionId)
        exoPlayer.addListener(playerListener)
        play()
    }

    fun play(mediaId: String) {

        mediaItems = mediaItems.filter { it.mediaId == mediaId }
            .plus(mediaItems.filter { it.mediaId != mediaId })

        exoPlayer.setMediaItems(mediaItems)

        exoPlayer.pause()
        exoPlayer.prepare()
        exoPlayer.play()
        currentMediaItem.onNext(mediaItems.first { it.mediaId == mediaId })
    }

    private fun play() {
        play(mediaItems.first().mediaId)
    }

    fun destroy() {
        exoPlayer.removeListener(playerListener)
        exoPlayer.release()
    }

    fun bindController(player: StyledPlayerControlView) {
        player.player = exoPlayer
    }
}