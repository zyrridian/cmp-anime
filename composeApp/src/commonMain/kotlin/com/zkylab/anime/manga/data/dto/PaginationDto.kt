package com.zkylab.anime.manga.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto(
    @SerialName("last_visible_page") val lastVisiblePage: Int? = null,
    @SerialName("has_next_page") val hasNextPage: Boolean? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("items") val items: PaginationItemsDto? = null
)

@Serializable
data class PaginationItemsDto(
    @SerialName("count") val count: Int? = null,
    @SerialName("total") val total: Int? = null,
    @SerialName("per_page") val perPage: Int? = null
)