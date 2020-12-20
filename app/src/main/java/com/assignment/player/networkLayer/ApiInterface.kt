package com.assignment.player.networkLayer

import com.assignment.player.room.photos.Photo
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET("/photos")
    fun fetchPhotos() : Single<List<Photo>>
}