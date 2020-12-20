package com.assignment.player.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.assignment.player.networkLayer.ApiService
import com.assignment.player.room.photos.Photo
import com.assignment.player.room.photos.PlayerRoomClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PhotosViewModel : ViewModel(), CoroutineScope {

    private var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    init {
        job = Job()
    }

    fun getAllPhotosAsLiveData(): LiveData<List<Photo>> {
        return PlayerRoomClient.playerAppDatabase.photoDao()!!.all
    }

    fun photosDataAlreadExist(): Boolean {
        return PlayerRoomClient.playerAppDatabase.photoDao()!!.dataSize.size != 0
    }

    fun photosSize(): Int{
        return PlayerRoomClient.playerAppDatabase.photoDao()!!.dataSize.size
    }

    fun callPhotosApi(){

        ApiService.buildService().fetchPhotos()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                launch {
                    onHandleResponse(response)
                }
            }
    }

    suspend fun onHandleResponse(response: List<Photo>){
        if(response.size != 0){
            response.forEach { photos ->
                withContext(Dispatchers.IO){
                    PlayerRoomClient.playerAppDatabase.photoDao()!!.insert(photos)
                }
            }
        }
    }
}