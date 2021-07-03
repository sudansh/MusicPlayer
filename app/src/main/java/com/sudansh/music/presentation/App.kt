package com.sudansh.music.presentation

import android.app.Application
import com.sudansh.music.BuildConfig
import com.sudansh.music.di.AppModule
import com.sudansh.music.di.RepoModule
import com.sudansh.music.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            // declare modules
            loadKoinModules(
                listOf(
                    AppModule,
                    RepoModule,
                    ViewModelModule
                )
            )
        }

    }
}