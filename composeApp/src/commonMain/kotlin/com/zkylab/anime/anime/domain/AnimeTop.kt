package com.zkylab.anime.anime.domain

data class AnimeTop(
    val malId: Int,
    val title: String,
    val imageUrl: String?,
    val score: Double?,
    val type: String?,
    val episodes: Int?,
    val rank: Int?,
    val popularity: Int?,
    val synopsis: String?,
    val status: String?,
    val aired: String?,
    val genres: List<String>,
    val studios: List<String>
)
