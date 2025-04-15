package com.zkylab.anime.anime.data.mappers

import com.zkylab.anime.anime.data.database.AnimeEntity
import com.zkylab.anime.anime.data.dto.*
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.domain.AnimeCharacter
import com.zkylab.anime.anime.domain.AnimePersonImages
import com.zkylab.anime.anime.domain.AnimeRecommendation
import com.zkylab.anime.anime.domain.AnimeStaff
import com.zkylab.anime.anime.domain.AnimeVoiceActor

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

// ----------------------------
// Anime Character Mapping
// ----------------------------

fun AnimeCharacterDto.toDomain(): AnimeCharacter? {
    val character = this.character ?: return null

    return AnimeCharacter(
        malId = character.malId ?: return null,
        name = character.name.orEmpty(),
        imageUrl = character.images?.jpg?.imageUrl.orEmpty(),
        url = character.url.orEmpty(),
        role = this.role.orEmpty(),
        favorites = this.favorites ?: 0,
        voiceActors = this.voiceActors?.mapNotNull { it.toDomain() } ?: emptyList()
    )
}

fun AnimeVoiceActorDto.toDomain(): AnimeVoiceActor? {
    val person = this.person ?: return null

    return AnimeVoiceActor(
        malId = person.malId ?: return null,
        name = person.name.orEmpty(),
        imageUrl = person.images?.jpg?.imageUrl.orEmpty(),
        url = person.url.orEmpty(),
        language = this.language.orEmpty()
    )
}


// ----------------------------
// Anime Staff Mapping
// ----------------------------

// Maps AnimeStaffResponseDto to a list of AnimeStaff
fun AnimeStaffResponseDto.toDomain(): List<AnimeStaff> {
    return data?.mapNotNull { it.toDomain() } ?: emptyList()
}

// Maps AnimeStaffDto to AnimeStaff
fun AnimeStaffDto.toDomain(): AnimeStaff? {
    val person = person ?: return null

    return AnimeStaff(
        malId = person.malId ?: return null,
        name = person.name.orEmpty(),
        imageUrl = person.images?.jpg?.imageUrl.orEmpty(),
        url = person.url.orEmpty(),
        positions = positions ?: emptyList()
    )
}

// Maps AnimePersonDto to AnimeStaff (Part of the mapping above)
fun AnimePersonDto.toPersonImages(): AnimePersonImages {
    return AnimePersonImages(
        imageUrl = images?.jpg?.imageUrl.orEmpty()
    )
}
