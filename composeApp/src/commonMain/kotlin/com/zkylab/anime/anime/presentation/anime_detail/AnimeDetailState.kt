package com.zkylab.anime.anime.presentation.anime_detail

import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.domain.AnimeCharacter
import com.zkylab.anime.anime.domain.AnimeRecommendation
import com.zkylab.anime.anime.domain.AnimeStaff

data class AnimeDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val anime: Anime? = null,
    val recommendations: List<AnimeRecommendation>? = null,
    val characters: List<AnimeCharacter>? = null,
    val staff: List<AnimeStaff>? = null
)
