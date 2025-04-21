package com.zkylab.anime.manga.data.dto

import com.zkylab.anime.anime.data.dto.PaginationDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("pagination") val pagination: PaginationDto? = null,
    @SerialName("data") val data: List<SearchedMangaDto>
)