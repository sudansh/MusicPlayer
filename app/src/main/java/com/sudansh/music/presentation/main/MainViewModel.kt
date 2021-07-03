package com.sudansh.music.presentation.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.sudansh.music.player.MusicService
import com.sudansh.music.presentation.base.BaseAndroidViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber


class MainViewModel(app: Application) : BaseAndroidViewModel(app), KoinComponent {
    private val musicService: MusicService by inject()

    val isPlayingVisible = MutableLiveData(true)
    val currentItem = MutableLiveData<MediaItem>()
    val playbackPosition = MutableLiveData<Long>()

    private val _audioSessionId = MutableLiveData<Int>()
    val audioSessionId: LiveData<Int> = _audioSessionId.distinctUntilChanged()

    private val nextItem = MutableLiveData<MediaItem>()

    val nextItemTitle = nextItem.map {
        it.mediaMetadata.title ?: "Playlist End"
    }
    val musicTitle = currentItem.map {
        it.mediaMetadata.title
    }
    val musicSubtitle = currentItem.map {
        it.mediaMetadata.artist.toString().plus(" | ").plus(it.mediaMetadata.year.toString())
    }

    init {
        addDisposable(musicService.observePlayingMedia()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currentItem.value = it
            })

        addDisposable(musicService.observeNextMedia()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                nextItem.value = it
            })
        addDisposable(musicService.observeAudioSessionId()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.i("xxx audiosession: $it")
                _audioSessionId.value = it
            })
    }

    override fun onCleared() {
        musicService.destroy()
        super.onCleared()
    }

    fun play(mediaId: String) {
        musicService.play(mediaId)
    }

    fun setPlayer(player: StyledPlayerControlView) {
        musicService.bindController(player)
    }

    fun setPlaybackProgress(position: Long) {
        playbackPosition.value = position
    }

}