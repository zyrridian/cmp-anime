package com.zkylab.anime.manga.data.repository

import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.Result
import com.zkylab.anime.core.domain.map
import com.zkylab.anime.manga.data.database.FavoriteMangaDao
import com.zkylab.anime.manga.data.mappers.toManga
import com.zkylab.anime.manga.data.network.RemoteMangaDataSource
import com.zkylab.anime.manga.domain.MangaRepository
import com.zkylab.anime.manga.domain.PaginatedMangaResult

class MangaRepositoryImpl(
    private val remoteMangaDataSource: RemoteMangaDataSource,
    private val favoriteMangaDao: FavoriteMangaDao
) : MangaRepository {
    override suspend fun searchManga(
        query: String,
        page: Int?
    ): Result<PaginatedMangaResult, DataError.Remote> {
        return remoteMangaDataSource
            .searchManga(query, page)
            .map { dto ->
                PaginatedMangaResult(
                    manga = dto.data.map { it.toManga() },
                    hasNextPage = dto.pagination?.hasNextPage ?: false
                )
            }
    }

//    override suspend fun getNewestManga(): Result<List<Manga>, DataError.Remote> {
//    }
//
//    override suspend fun getTopManga(): Result<List<Manga>, DataError.Remote> {
//    }
//
//    override suspend fun getMangaByGenre(genreId: Int): Result<List<Manga>, DataError.Remote> {
//    }
//
//    override suspend fun getMangaDetails(mangaId: Int): Result<Manga, DataError.Remote> {
//    }
//
//    override suspend fun searchManga(
//        query: String,
//        page: Int
//    ): Result<List<Manga>, DataError.Remote> {
//    }
//
//    override suspend fun getFavoriteManga(): Result<List<Manga>, DataError.Remote> {
//    }
//
//    override suspend fun toggleFavorite(mangaId: Int): Result<Boolean, DataError.Remote> {
//    }

}