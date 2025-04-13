package com.zkylab.anime.anime.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AnimeDatabaseConstructor: RoomDatabaseConstructor<FavoriteAnimeDatabase> {
    override fun initialize(): FavoriteAnimeDatabase
}