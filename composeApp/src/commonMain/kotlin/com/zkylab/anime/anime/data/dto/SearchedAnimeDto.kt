package com.zkylab.anime.anime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedAnimeDto(
    @SerialName("mal_id") val malId: Int? = null,
    val title: String? = null,
    val synopsis: String? = null,
    val score: Double? = null,
    val type: String? = null,
    val rank: Int? = null,
    val popularity: Int? = null,
    val episodes: Int? = null,
    val duration: String? = null,
    val status: String? = null,
    val aired: AiredDto? = null,
    val studios: List<StudioDto>? = null,
    val genres: List<GenreDto>? = null,
    val images: ImageWrapper? = null
)

@Serializable
data class AiredDto(
    val string: String? = null
)

@Serializable
data class StudioDto(
    val name: String? = null
)

@Serializable
data class GenreDto(
    val name: String? = null
)

@Serializable
data class ImageWrapper(
    val jpg: JpgImage? = null
)

@Serializable
data class JpgImage(
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("small_image_url") val smallImageUrl: String? = null,
    @SerialName("large_image_url") val largeImageUrl: String? = null
)
