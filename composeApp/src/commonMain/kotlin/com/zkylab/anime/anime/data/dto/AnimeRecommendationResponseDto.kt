package com.zkylab.anime.anime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRecommendationResponseDto(
    @SerialName("data")
    val data: List<AnimeRecommendationDto>? = null
)

@Serializable
data class AnimeRecommendationDto(
    @SerialName("url")
    val url: String? = null,

    @SerialName("votes")
    val votes: Int? = null,

    @SerialName("entry")
    val entry: AnimeRecommendationEntryDto? = null
)

@Serializable
data class AnimeRecommendationEntryDto(
    @SerialName("mal_id")
    val malId: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("images")
    val images: AnimeRecommendationImagesDto? = null
)

@Serializable
data class AnimeRecommendationImagesDto(
    @SerialName("jpg")
    val jpg: AnimeRecommendationImageFormatDto? = null,

    @SerialName("webp")
    val webp: AnimeRecommendationImageFormatDto? = null
)

@Serializable
data class AnimeRecommendationImageFormatDto(
    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("small_image_url")
    val smallImageUrl: String? = null,

    @SerialName("large_image_url")
    val largeImageUrl: String? = null
)
