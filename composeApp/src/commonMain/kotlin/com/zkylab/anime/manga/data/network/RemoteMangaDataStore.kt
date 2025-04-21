package com.zkylab.anime.manga.data.network

import com.zkylab.anime.manga.data.dto.SearchResponseDto
import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.Result

interface RemoteMangaDataSource {
    suspend fun searchManga(
        query: String,
        page: Int?,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

//    suspend fun getAnimeDetails(animeId: String): Result<SearchedAnimeDto, DataError.Remote>
//    suspend fun getAnimeRecommendations(animeId: String): Result<AnimeRecommendationResponseDto, DataError.Remote>
//    suspend fun getAnimeCharacters(animeId: String): Result<AnimeCharacterResponseDto, DataError.Remote>
//    suspend fun getAnimeStaff(animeId: String): Result<AnimeStaffResponseDto, DataError.Remote>
}