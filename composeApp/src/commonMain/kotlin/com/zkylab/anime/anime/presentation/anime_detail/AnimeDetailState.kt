package com.zkylab.anime.anime.presentation.anime_detail

import com.zkylab.anime.anime.domain.Anime

data class AnimeDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val anime: Anime? = null
)
