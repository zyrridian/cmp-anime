package com.zkylab.anime.anime.domain

import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.EmptyResult
import com.zkylab.anime.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun searchAnime(query: String, page: Int?): Result<PaginatedAnimeResult, DataError.Remote>
    suspend fun getAnimeDescription(animeId: String): Result<String?, DataError>
    suspend fun getAnimeRecommendations(animeId: String): Result<List<AnimeRecommendation>, DataError.Remote>
    suspend fun getAnimeCharacters(animeId: String): Result<List<AnimeCharacter>, DataError.Remote>

    fun getFavoriteAnime(): Flow<List<Anime>>
    fun isAnimeFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(anime: Anime): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}