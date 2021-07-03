package com.sudansh.music.presentation.lyrics

import androidx.lifecycle.*
import com.sudansh.music.datasource.MediaDataSource
import com.sudansh.music.model.Lyrics
import kotlinx.coroutines.launch

class LyricsViewModel(private val mediaDataSource: MediaDataSource) : ViewModel() {

    private val _lyrics = MutableLiveData<Lyrics>()
    val lyrics: LiveData<Lyrics> = _lyrics.distinctUntilChanged()

    private val _currentLyricsPositionData = MutableLiveData<Int>()
    val currentLyricsPositionData: LiveData<Int> = _currentLyricsPositionData

    fun getLyrics(mediaId: String, title: String) {
        viewModelScope.launch {
            _lyrics.value = mediaDataSource.getLyrics(mediaId, title)
        }
    }

    fun setPlaybackPosition(position: Long) {
        val lyrics = _lyrics.value?.lyricItems ?: return
        for (i in 0 until lyrics.size - 1) {
            if (position <= lyrics[i + 1].timeStampMillis) {
                _currentLyricsPositionData.value = i
                break
            }
        }
    }
}
