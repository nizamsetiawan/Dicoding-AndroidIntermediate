package com.example.storysnap.data.remote.network

import com.example.storysnap.data.remote.request.LoginRequest
import com.example.storysnap.data.remote.request.RegisterRequest
import com.example.storysnap.data.remote.response.AddNewStoryResponse
import com.example.storysnap.data.remote.response.AllStoryResponse
import com.example.storysnap.data.remote.response.DetailStoryResponse
import com.example.storysnap.data.remote.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST("login")
    suspend fun login(
        @Body login : LoginRequest
    ): Response<LoginResponse>

    @POST("register")
    suspend fun register(
        @Body login : RegisterRequest
    ):Response<LoginResponse>

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Response<AddNewStoryResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<AllStoryResponse>

    @GET("stories/{id}")
    fun detailStory(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DetailStoryResponse>

    @GET("stories")
    fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1
    ): Call<AllStoryResponse>
}