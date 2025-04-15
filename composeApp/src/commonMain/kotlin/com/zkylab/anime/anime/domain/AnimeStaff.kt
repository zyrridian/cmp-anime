package com.zkylab.anime.anime.domain

data class AnimeStaff(
    val malId: Int,
    val name: String,
    val imageUrl: String,
    val url: String,
    val positions: List<String>
)

data class AnimePersonImages(
    val imageUrl: String
)
