package com.zkylab.anime.anime.data.mappers

import com.zkylab.anime.anime.data.database.AnimeEntity
import com.zkylab.anime.anime.data.database.AnimeRecommendationEntity
import com.zkylab.anime.anime.data.dto.*
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.domain.AnimeRecommendation

// ----------------------------
// Main Anime Mapping
// ----------------------------

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

// ----------------------------
// Anime Recommendation Mapping
// ----------------------------

fun AnimeRecommendationDto.toEntity(): AnimeRecommendationEntity {
    return AnimeRecommendationEntity(
        malId = entry?.malId,
        title = entry?.title,
        imageUrl = entry?.images?.jpg?.imageUrl,
        url = entry?.url,
        votes = votes
    )
}

fun AnimeRecommendationEntity.toDomain(): AnimeRecommendation {
    return AnimeRecommendation(
        malId = malId ?: -1,
        title = title.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        url = url.orEmpty(),
        votes = votes ?: 0
    )
}

fun AnimeRecommendationDto.toDomain(): AnimeRecommendation? {
    val entry = this.entry ?: return null
    return AnimeRecommendation(
        malId = entry.malId ?: -1,
        title = entry.title.orEmpty(),
        imageUrl = entry.images?.jpg?.imageUrl.orEmpty(),
        url = entry.url.orEmpty(),
        votes = this.votes ?: 0
    )
}

fun List<AnimeRecommendationDto>?.toDomainList(): List<AnimeRecommendation> {
    return this?.mapNotNull { it.toDomain() } ?: emptyList()
}
