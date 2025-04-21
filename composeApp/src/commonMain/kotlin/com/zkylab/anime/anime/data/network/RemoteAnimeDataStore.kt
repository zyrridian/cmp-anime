package com.zkylab.anime.anime.data.network

import com.zkylab.anime.anime.data.dto.AnimeCharacterResponseDto
import com.zkylab.anime.anime.data.dto.AnimeRecommendationResponseDto
import com.zkylab.anime.anime.data.dto.AnimeStaffResponseDto
import com.zkylab.anime.anime.data.dto.AnimeTopResponseDto
import com.zkylab.anime.anime.data.dto.SearchResponseDto
import com.zkylab.anime.anime.data.dto.SearchedAnimeDto
import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.Result

interface RemoteAnimeDataSource {
    suspend fun searchAnime(
        query: String,
        page: Int?,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getAnimeDetails(animeId: String): Result<SearchedAnimeDto, DataError.Remote>
    suspend fun getAnimeRecommendations(animeId: String): Result<AnimeRecommendationResponseDto, DataError.Remote>
    suspend fun getAnimeCharacters(animeId: String): Result<AnimeCharacterResponseDto, DataError.Remote>
    suspend fun getAnimeStaff(animeId: String): Result<AnimeStaffResponseDto, DataError.Remote>

    /**
     * Fetch top anime from the Jikan API with various filters.
     *
     * @param page The page number for pagination (optional).
     * @param limit The number of results per page (optional).
     * @param type Filter by anime type (e.g., "tv", "movie", "ova").
     * @param filter Filter type (e.g., "airing", "upcoming", "bypopularity", "favorite").
     * @param rating Filter by audience rating (e.g., "g", "pg", "pg13", "r17", "r", "rx").
     * @param sfw If true, filters out adult content (Safe For Work).
     * @return Result containing [AnimeTopResponseDto] or [DataError.Remote]
     */
    suspend fun getTopAnime(
        page: Int? = null,
        limit: Int? = null,
        type: String? = null,
        filter: String? = null,
        rating: String? = null,
        sfw: Boolean? = null
    ): Result<AnimeTopResponseDto, DataError.Remote>

}