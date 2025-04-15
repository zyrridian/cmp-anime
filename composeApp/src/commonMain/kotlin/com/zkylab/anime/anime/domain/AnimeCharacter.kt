package com.zkylab.anime.anime.domain

data class AnimeCharacter(
    val malId: Int,
    val name: String,
    val imageUrl: String,
    val url: String,
    val role: String,
    val favorites: Int,
    val voiceActors: List<AnimeVoiceActor>
)

data class AnimeVoiceActor(
    val malId: Int,
    val name: String,
    val imageUrl: String,
    val url: String,
    val language: String
)
