package com.assignment.player.room.photos

import androidx.room.Room
import com.assignment.player.support.MyApplication

object PlayerRoomClient {
    //our app database object
    val playerAppDatabase: PlayerRoomDatabase

    init {
        playerAppDatabase = Room.databaseBuilder(MyApplication.applicationContext(), PlayerRoomDatabase::class.java, "Assignment").build()
    }
}