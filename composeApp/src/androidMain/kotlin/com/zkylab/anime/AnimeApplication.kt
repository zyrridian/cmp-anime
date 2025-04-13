package com.zkylab.anime

import android.app.Application
import com.zkylab.anime.di.initKoin
import org.koin.android.ext.koin.androidContext

class AnimeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AnimeApplication)
        }
    }
}