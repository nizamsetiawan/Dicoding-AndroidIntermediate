package com.example.storysnap.data.remote


import com.example.storysnap.data.remote.network.ApiService
import com.example.storysnap.data.remote.request.LoginRequest
import com.example.storysnap.data.remote.request.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource(private val api : ApiService) {
    suspend fun login(data : LoginRequest) = api.login(data)
    suspend fun register(data : RegisterRequest) = api.register(data)
    suspend fun getStories(token : String, page : Int, size : Int) = api.getStories(token, page, size)
    fun getDetailStories(token : String, id : String) = api.detailStory(token, id)
    suspend fun uploadStories(token : String, fileImage : MultipartBody.Part, description : RequestBody) = api.addNewStory(token, fileImage, description)
    fun getStoriesLocation(token : String) = api.getStoryLocation(token)

}