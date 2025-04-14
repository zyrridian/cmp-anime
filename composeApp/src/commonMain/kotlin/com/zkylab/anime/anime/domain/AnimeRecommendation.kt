package com.zkylab.anime.anime.domain

data class AnimeRecommendation(
    val malId: Int,
    val title: String,
    val imageUrl: String,
    val url: String,
    val votes: Int
)