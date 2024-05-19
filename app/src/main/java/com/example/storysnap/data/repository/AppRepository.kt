package com.example.storysnap.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storysnap.data.prefs.UserModel
import com.example.storysnap.data.prefs.UserPreference
import com.example.storysnap.data.remote.RemoteDataSource
import com.example.storysnap.data.remote.network.Resource
import com.example.storysnap.data.remote.request.LoginRequest
import com.example.storysnap.data.remote.request.RegisterRequest
import com.example.storysnap.data.remote.response.AddNewStoryResponse
import com.example.storysnap.data.remote.response.AllStoryResponse
import com.example.storysnap.data.remote.response.DetailStoryResponse
import com.example.storysnap.data.remote.response.ListStoryItem
import com.example.storysnap.data.remote.response.LoginResponse
import com.example.storysnap.data.remote.response.Story
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class AppRepository(
    private val remoteDataSource: RemoteDataSource,
    private val preference: UserPreference
) {
//    private val _listStories = MutableLiveData<List<ListStoryItem>>()
//    val listStories: LiveData<List<ListStoryItem>> = _listStories

    private val _detail = MutableLiveData<Story>()
    val detail: LiveData<Story> = _detail

    private val _storyLocation = MutableLiveData<List<ListStoryItem>>()
    val storyLocation: LiveData<List<ListStoryItem>> = _storyLocation

    fun login(data: LoginRequest) = liveData {
        emit(Resource.Loading)
        try {
            val response = remoteDataSource.login(data)
            val dataResponse = response.body()?.loginResult?.token
            emit(Resource.Success(dataResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(Resource.Error(errorResponse.message!!))

        }
    }

    fun register(data: RegisterRequest) = liveData {
        emit(Resource.Loading)
        try {
            val response = remoteDataSource.register(data)
            val dataResponse = response.body()?.message
            emit(Resource.Success(dataResponse))

        } catch (e: HttpException) {
            val errorMessage: String
            if (e.code() == 400) {
                errorMessage = "Email sudah dimasukan"
                emit(Resource.Error(errorMessage))
            } else {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, AddNewStoryResponse::class.java)
                errorMessage = errorBody.message.toString()
                emit(Resource.Error(errorMessage))
            }
        }
    }

    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPaging(remoteDataSource, "Bearer $token")
            }
        ).liveData
    }


    fun getDetailStories(token: String, id: String) {
        val responseDetail = remoteDataSource.getDetailStories("Bearer $token", id)
        responseDetail.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _detail.value = response.body()?.story!!
                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
                }

            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun addNewStory(token: String, file: File, description: String) = liveData {
        emit(Resource.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestFile =file.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        try {
            val response = remoteDataSource.uploadStories("Bearer $token", multipartBody, requestBody)
            val dataResponse = response.body()?.message
            emit(Resource.Success(dataResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddNewStoryResponse::class.java)
            emit(Resource.Error(errorResponse.message!!))
        }
    }
    fun getStoriesWithLocation(token: String){
        val responseLocation = remoteDataSource.getStoriesLocation("Bearer $token")
        responseLocation.enqueue(object : Callback<AllStoryResponse> {
            override fun onResponse(
                call: Call<AllStoryResponse>, response: Response<AllStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _storyLocation.value = responseBody.listStory
                    }
                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }



    fun getSession(): Flow<UserModel> {
        return preference.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        preference.saveSession(user)
    }

    suspend fun logout() {
        preference.logout()
    }


}