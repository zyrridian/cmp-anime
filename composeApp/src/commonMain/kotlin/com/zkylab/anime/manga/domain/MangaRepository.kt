package com.zkylab.anime.manga.domain

import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.Result

interface MangaRepository {
    suspend fun searchManga(query: String, page: Int?): Result<PaginatedMangaResult, DataError.Remote>
//    suspend fun getNewestManga(): Result<List<Manga>, DataError.Remote>
//    suspend fun getTopManga(): Result<List<Manga>, DataError.Remote>
//    suspend fun getMangaByGenre(genreId: Int): Result<List<Manga>, DataError.Remote>
//    suspend fun getMangaDetails(mangaId: Int): Result<Manga, DataError.Remote>
//    suspend fun searchManga(query: String, page: Int): Result<List<Manga>, DataError.Remote>
//    suspend fun getFavoriteManga(): Result<List<Manga>, DataError.Remote>
//    suspend fun toggleFavorite(mangaId: Int): Result<Boolean, DataError.Remote>
}

//interface AnimeRepository {
//    suspend fun searchAnime(query: String, page: Int?): Result<PaginatedAnimeResult, DataError.Remote>
//    suspend fun getAnimeDescription(animeId: String): Result<String?, DataError>
//    suspend fun getAnimeRecommendations(animeId: String): Result<List<AnimeRecommendation>, DataError.Remote>
//    suspend fun getAnimeCharacters(animeId: String): Result<List<AnimeCharacter>, DataError.Remote>
//    suspend fun getAnimeStaff(animeId: String): Result<List<AnimeStaff>, DataError.Remote>
//    fun getFavoriteAnime(): Flow<List<Anime>>
//    fun isAnimeFavorite(id: String): Flow<Boolean>
//    suspend fun markAsFavorite(anime: Anime): EmptyResult<DataError.Local>
//    suspend fun deleteFromFavorites(id: String)
//}