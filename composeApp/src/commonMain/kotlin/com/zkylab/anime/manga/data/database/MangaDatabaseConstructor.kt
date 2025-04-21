package com.zkylab.anime.manga.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object MangaDatabaseConstructor: RoomDatabaseConstructor<FavoriteMangaDatabase> {
    override fun initialize(): FavoriteMangaDatabase
}