package com.zkylab.anime.anime.presentation.anime_list

import com.zkylab.anime.anime.domain.Anime

sealed interface AnimeListAction {
    data class OnSearchQueryChange(val query: String): AnimeListAction
    data class OnAnimeClick(val anime: Anime): AnimeListAction
    data class OnTabSelected(val index: Int): AnimeListAction
}