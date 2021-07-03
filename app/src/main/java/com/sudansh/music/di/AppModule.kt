package com.sudansh.music.di

import com.sudansh.music.datasource.MediaDataSource
import com.sudansh.music.datasource.MediaDataSourceImp
import com.sudansh.music.player.MusicService
import com.sudansh.music.presentation.lyrics.LyricsViewModel
import com.sudansh.music.presentation.main.MainViewModel
import com.sudansh.music.presentation.playlist.PlayListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    single { MusicService(androidApplication(), get()) }
}
val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { PlayListViewModel(get()) }
    viewModel { LyricsViewModel(get()) }
}

val RepoModule = module {
    single<MediaDataSource> { MediaDataSourceImp(androidContext()) }
}