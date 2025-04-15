package com.zkylab.anime.anime.data.network

import com.zkylab.anime.anime.data.dto.AnimeCharacterResponseDto
import com.zkylab.anime.anime.data.dto.AnimeRecommendationResponseDto
import com.zkylab.anime.anime.data.dto.AnimeStaffResponseDto
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
}