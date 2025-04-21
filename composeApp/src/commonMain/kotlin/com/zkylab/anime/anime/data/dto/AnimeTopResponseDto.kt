package com.zkylab.anime.anime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTopResponseDto(
    @SerialName("data")
    val data: List<AnimeTopDto>? = null,

    @SerialName("pagination")
    val pagination: AnimeTopPaginationDto? = null
)

@Serializable
data class AnimeTopDto(
    @SerialName("mal_id")
    val malId: Int? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("images")
    val images: AnimeTopImagesDto? = null,

    @SerialName("trailer")
    val trailer: AnimeTopTrailerDto? = null,

    @SerialName("approved")
    val approved: Boolean? = null,

    @SerialName("titles")
    val titles: List<AnimeTopTitleDto>? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("title_english")
    val titleEnglish: String? = null,

    @SerialName("title_japanese")
    val titleJapanese: String? = null,

    @SerialName("title_synonyms")
    val titleSynonyms: List<String>? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("source")
    val source: String? = null,

    @SerialName("episodes")
    val episodes: Int? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("airing")
    val airing: Boolean? = null,

    @SerialName("aired")
    val aired: AnimeTopAiredDto? = null,

    @SerialName("duration")
    val duration: String? = null,

    @SerialName("rating")
    val rating: String? = null,

    @SerialName("score")
    val score: Double? = null,

    @SerialName("scored_by")
    val scoredBy: Int? = null,

    @SerialName("rank")
    val rank: Int? = null,

    @SerialName("popularity")
    val popularity: Int? = null,

    @SerialName("members")
    val members: Int? = null,

    @SerialName("favorites")
    val favorites: Int? = null,

    @SerialName("synopsis")
    val synopsis: String? = null,

    @SerialName("background")
    val background: String? = null,

    @SerialName("season")
    val season: String? = null,

    @SerialName("year")
    val year: Int? = null,

    @SerialName("broadcast")
    val broadcast: AnimeTopBroadcastDto? = null,

    @SerialName("producers")
    val producers: List<AnimeTopCompanyDto>? = null,

    @SerialName("licensors")
    val licensors: List<AnimeTopCompanyDto>? = null,

    @SerialName("studios")
    val studios: List<AnimeTopCompanyDto>? = null,

    @SerialName("genres")
    val genres: List<AnimeTopCompanyDto>? = null,

    @SerialName("explicit_genres")
    val explicitGenres: List<AnimeTopCompanyDto>? = null,

    @SerialName("themes")
    val themes: List<AnimeTopCompanyDto>? = null,

    @SerialName("demographics")
    val demographics: List<AnimeTopCompanyDto>? = null
)

@Serializable
data class AnimeTopImagesDto(
    @SerialName("jpg")
    val jpg: AnimeTopImageFormatDto? = null,

    @SerialName("webp")
    val webp: AnimeTopImageFormatDto? = null
)

@Serializable
data class AnimeTopImageFormatDto(
    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("small_image_url")
    val smallImageUrl: String? = null,

    @SerialName("large_image_url")
    val largeImageUrl: String? = null
)

@Serializable
data class AnimeTopTrailerDto(
    @SerialName("youtube_id")
    val youtubeId: String? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("embed_url")
    val embedUrl: String? = null
)

@Serializable
data class AnimeTopTitleDto(
    @SerialName("type")
    val type: String? = null,

    @SerialName("title")
    val title: String? = null
)

@Serializable
data class AnimeTopAiredDto(
    @SerialName("from")
    val from: String? = null,

    @SerialName("to")
    val to: String? = null,

    @SerialName("prop")
    val prop: AnimeTopAiredPropDto? = null
)

@Serializable
data class AnimeTopAiredPropDto(
    @SerialName("from")
    val from: AnimeTopDateDto? = null,

    @SerialName("to")
    val to: AnimeTopDateDto? = null,

    @SerialName("string")
    val string: String? = null
)

@Serializable
data class AnimeTopDateDto(
    @SerialName("day")
    val day: Int? = null,

    @SerialName("month")
    val month: Int? = null,

    @SerialName("year")
    val year: Int? = null
)

@Serializable
data class AnimeTopBroadcastDto(
    @SerialName("day")
    val day: String? = null,

    @SerialName("time")
    val time: String? = null,

    @SerialName("timezone")
    val timezone: String? = null,

    @SerialName("string")
    val string: String? = null
)

@Serializable
data class AnimeTopCompanyDto(
    @SerialName("mal_id")
    val malId: Int? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class AnimeTopPaginationDto(
    @SerialName("last_visible_page")
    val lastVisiblePage: Int? = null,

    @SerialName("has_next_page")
    val hasNextPage: Boolean? = null,

    @SerialName("current_page")
    val currentPage: Int? = null,

    @SerialName("items")
    val items: AnimeTopPaginationItemsDto? = null
)

@Serializable
data class AnimeTopPaginationItemsDto(
    @SerialName("count")
    val count: Int? = null,

    @SerialName("total")
    val total: Int? = null,

    @SerialName("per_page")
    val perPage: Int? = null
)
