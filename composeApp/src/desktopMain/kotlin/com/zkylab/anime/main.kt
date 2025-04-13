package com.zkylab.anime

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.zkylab.anime.app.App
import com.zkylab.anime.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "cmp-anime",
        ) {
            App()
        }
    }
}