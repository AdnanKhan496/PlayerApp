package com.assignment.player.room.photos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Photo : Serializable {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "url")
    var url: String? = null

    @ColumnInfo(name = "thumbnailUrl")
    var thumbnailUrl: String? = null
}