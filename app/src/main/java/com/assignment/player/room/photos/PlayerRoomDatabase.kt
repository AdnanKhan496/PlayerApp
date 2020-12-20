package com.assignment.player.room.photos

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Photo::class], version = 1)
abstract class PlayerRoomDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao?
}