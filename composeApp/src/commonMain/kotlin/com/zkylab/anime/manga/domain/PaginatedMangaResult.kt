package com.zkylab.anime.manga.domain

data class PaginatedMangaResult(
    val manga: List<Manga>,
    val hasNextPage: Boolean
)