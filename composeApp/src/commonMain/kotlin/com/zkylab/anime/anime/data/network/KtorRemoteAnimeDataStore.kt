package com.zkylab.anime.anime.data.network

import com.zkylab.anime.anime.data.dto.AnimeCharacterResponseDto
import com.zkylab.anime.anime.data.dto.AnimeRecommendationResponseDto
import com.zkylab.anime.anime.data.dto.AnimeStaffResponseDto
import com.zkylab.anime.core.data.safeCall
import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.Result
import com.zkylab.anime.anime.data.dto.SearchResponseDto
import com.zkylab.anime.anime.data.dto.SearchedAnimeDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.jikan.moe/v4"

class KtorRemoteAnimeDataSource(
    private val httpClient: HttpClient
) : RemoteAnimeDataSource {

    override suspend fun searchAnime(
        query: String,
        page: Int?,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/anime"
            ) {
                parameter("q", query)
                parameter("page", page)
            }
        }
    }

    override suspend fun getAnimeDetails(animeId: String): Result<SearchedAnimeDto, DataError.Remote> {
        return safeCall<SearchedAnimeDto> {
            httpClient.get(
                urlString = "$BASE_URL/anime/$animeId"
            )
        }
    }

    override suspend fun getAnimeRecommendations(animeId: String): Result<AnimeRecommendationResponseDto, DataError.Remote> {
        return safeCall<AnimeRecommendationResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/anime/$animeId/recommendations"
            )
        }
    }

    override suspend fun getAnimeCharacters(animeId: String): Result<AnimeCharacterResponseDto, DataError.Remote> {
        return safeCall<AnimeCharacterResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/anime/$animeId/characters"
            )
        }
    }

    override suspend fun getAnimeStaff(animeId: String): Result<AnimeStaffResponseDto, DataError.Remote> {
        return safeCall<AnimeStaffResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/anime/$animeId/staff"
            )
        }
    }
}