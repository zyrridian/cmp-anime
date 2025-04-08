package com.zkylab.anime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform