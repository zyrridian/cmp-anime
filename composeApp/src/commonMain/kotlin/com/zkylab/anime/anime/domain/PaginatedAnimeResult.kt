package com.zkylab.anime.anime.domain

data class PaginatedAnimeResult(
    val anime: List<Anime>,
    val hasNextPage: Boolean
)