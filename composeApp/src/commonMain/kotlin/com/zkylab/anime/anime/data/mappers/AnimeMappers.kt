package com.zkylab.anime.anime.data.mappers

import com.zkylab.anime.anime.data.database.AnimeEntity
import com.zkylab.anime.anime.data.dto.SearchedAnimeDto
import com.zkylab.anime.anime.domain.Anime

fun SearchedAnimeDto.toAnime(): Anime {
    return Anime(
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

fun Anime.toAnimeEntity(): AnimeEntity {
    return AnimeEntity(
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

fun AnimeEntity.toAnime(): Anime {
    return Anime(
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