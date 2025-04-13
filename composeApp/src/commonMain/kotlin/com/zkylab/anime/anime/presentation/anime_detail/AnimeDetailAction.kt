package com.zkylab.anime.anime.presentation.anime_detail

import com.zkylab.anime.anime.domain.Anime

sealed interface AnimeDetailAction {
    data object OnBackClick: AnimeDetailAction
    data object OnFavoriteClick: AnimeDetailAction
    data class OnSelectedAnimeChange(val anime: Anime): AnimeDetailAction
}