package com.zkylab.anime.anime.data.repository

import androidx.sqlite.SQLiteException
import com.zkylab.anime.anime.data.database.FavoriteAnimeDao
import com.zkylab.anime.anime.data.mappers.toAnime
import com.zkylab.anime.anime.data.mappers.toAnimeEntity
import com.zkylab.anime.anime.data.mappers.toDomain
import com.zkylab.anime.anime.data.network.RemoteAnimeDataSource
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.domain.AnimeRecommendation
import com.zkylab.anime.anime.domain.AnimeRepository
import com.zkylab.anime.anime.domain.PaginatedAnimeResult
import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.EmptyResult
import com.zkylab.anime.core.domain.Result
import com.zkylab.anime.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAnimeRepository(
    private val remoteAnimeDataSource: RemoteAnimeDataSource,
    private val favoriteAnimeDao: FavoriteAnimeDao
) : AnimeRepository {
    override suspend fun searchAnime(
        query: String,
        page: Int?
    ): Result<PaginatedAnimeResult, DataError.Remote> {
        return remoteAnimeDataSource
            .searchAnime(query, page)
            .map { dto ->
                PaginatedAnimeResult(
                    anime = dto.data.map { it.toAnime() },
                    hasNextPage = dto.pagination?.hasNextPage ?: false
                )
            }
    }

    override suspend fun getAnimeDescription(animeId: String): Result<String?, DataError> {
        val localResult = favoriteAnimeDao.getFavoriteAnime(animeId)

        return if (localResult == null) {
            remoteAnimeDataSource
                .getAnimeDetails(animeId)
                .map { it.synopsis }
        } else {
            Result.Success(localResult.synopsis)
        }
    }

    override suspend fun getAnimeRecommendations(animeId: String): Result<List<AnimeRecommendation>, DataError.Remote> {
        return remoteAnimeDataSource
            .getAnimeRecommendations(animeId)
            .map { response ->
                response.data
                    ?.mapNotNull { it.toDomain() }
                    ?: emptyList()
            }
    }

    override fun getFavoriteAnime(): Flow<List<Anime>> {
        return favoriteAnimeDao
            .getFavoriteAnime()
            .map { animeEntities ->
                animeEntities.map { it.toAnime() }
            }
    }

    override fun isAnimeFavorite(id: String): Flow<Boolean> {
        return favoriteAnimeDao
            .getFavoriteAnime()
            .map { animeEntities ->
                animeEntities.any { it.malId.toString() == id }
            }
    }

    override suspend fun markAsFavorite(anime: Anime): EmptyResult<DataError.Local> {
        return try {
            favoriteAnimeDao.upsert(anime.toAnimeEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteAnimeDao.deleteFavoriteAnime(id)
    }
}