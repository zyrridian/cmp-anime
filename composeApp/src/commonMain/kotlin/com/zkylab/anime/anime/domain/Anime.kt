package com.zkylab.anime.anime.domain

data class Anime(
    val malId: Int?,
    val title: String?,
    val imageUrl: String?,
    val synopsis: String?,
    val score: Double?,
    val type: String?,
    val rank: Int?,
    val popularity: Int?,
    val episodes: Int?,
    val duration: String?,
    val status: String?,
    val aired: String?,
    val studios: List<String>?,
    val genres: List<String>?
)
