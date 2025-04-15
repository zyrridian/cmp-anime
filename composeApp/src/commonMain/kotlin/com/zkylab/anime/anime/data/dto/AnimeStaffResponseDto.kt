package com.zkylab.anime.anime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeStaffResponseDto(
    @SerialName("data")
    val data: List<AnimeStaffDto>? = null
)

@Serializable
data class AnimeStaffDto(
    @SerialName("person")
    val person: AnimePersonDto? = null,

    @SerialName("positions")
    val positions: List<String>? = null
)

@Serializable
data class AnimePersonDto(
    @SerialName("mal_id")
    val malId: Int? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("images")
    val images: AnimePersonImagesDto? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class AnimePersonImagesDto(
    @SerialName("jpg")
    val jpg: AnimePersonImageFormatDto? = null
)

@Serializable
data class AnimePersonImageFormatDto(
    @SerialName("image_url")
    val imageUrl: String? = null
)
