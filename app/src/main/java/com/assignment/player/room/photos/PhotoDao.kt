package com.assignment.player.room.photos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhotoDao {
    @get:Query("SELECT * FROM photo")
    val all: LiveData<List<Photo>>

    @get:Query("SELECT * FROM photo")
    val dataSize: List<Photo>

    @Insert
    fun insert(task: Photo?)

    @Delete
    fun delete(task: Photo?)

    @Update
    fun update(task: Photo?)
}