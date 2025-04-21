package com.zkylab.anime.manga.data.mappers

import com.zkylab.anime.manga.data.database.MangaEntity
import com.zkylab.anime.manga.data.dto.*
import com.zkylab.anime.manga.domain.Manga

// ----------------------------
// Main Manga Mapping
// ----------------------------

fun SearchedMangaDto.toManga(): Manga {
    return Manga(
        malId = malId,
        title = title,
        synopsis = synopsis,
        score = score,
        type = type,
        rank = rank,
        popularity = popularity,
        episodes = episodes,
        duration = duration,
        status = status,
        aired = aired?.string,
        studios = studios?.mapNotNull { it.name } ?: emptyList(),
        genres = genres?.mapNotNull { it.name } ?: emptyList(),
        imageUrl = images?.jpg?.imageUrl
    )
}

fun Manga.toMangaEntity(): MangaEntity {
    return MangaEntity(
        malId = malId,
        title = title,
        synopsis = synopsis,
        score = score,
        type = type,
        rank = rank,
        popularity = popularity,
        episodes = episodes,
        duration = duration,
        status = status,
        aired = aired,
        studios = studios,
        genres = genres,
        imageUrl = imageUrl
    )
}

fun MangaEntity.toManga(): Manga {
    return Manga(
        malId = malId,
        title = title,
        synopsis = synopsis,
        score = score,
        type = type,
        rank = rank,
        popularity = popularity,
        episodes = episodes,
        duration = duration,
        status = status,
        aired = aired,
        studios = studios,
        genres = genres,
        imageUrl = imageUrl
    )
}
