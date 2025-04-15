package com.zkylab.anime.anime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeCharacterResponseDto(
    @SerialName("data")
    val data: List<AnimeCharacterDto>? = null
)

@Serializable
data class AnimeCharacterDto(
    @SerialName("character")
    val character: AnimeCharacterEntryDto? = null,

    @SerialName("role")
    val role: String? = null,

    @SerialName("favorites")
    val favorites: Int? = null,

    @SerialName("voice_actors")
    val voiceActors: List<AnimeVoiceActorDto>? = null
)

@Serializable
data class AnimeCharacterEntryDto(
    @SerialName("mal_id")
    val malId: Int? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("images")
    val images: AnimeCharacterImagesDto? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class AnimeCharacterImagesDto(
    @SerialName("jpg")
    val jpg: AnimeCharacterImageFormatDto? = null,

    @SerialName("webp")
    val webp: AnimeCharacterImageFormatDto? = null
)

@Serializable
data class AnimeCharacterImageFormatDto(
    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("small_image_url")
    val smallImageUrl: String? = null
)

@Serializable
data class AnimeVoiceActorDto(
    @SerialName("person")
    val person: AnimeVoiceActorPersonDto? = null,

    @SerialName("language")
    val language: String? = null
)

@Serializable
data class AnimeVoiceActorPersonDto(
    @SerialName("mal_id")
    val malId: Int? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("images")
    val images: AnimeVoiceActorImagesDto? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class AnimeVoiceActorImagesDto(
    @SerialName("jpg")
    val jpg: AnimeVoiceActorImageFormatDto? = null
)

@Serializable
data class AnimeVoiceActorImageFormatDto(
    @SerialName("image_url")
    val imageUrl: String? = null
)
