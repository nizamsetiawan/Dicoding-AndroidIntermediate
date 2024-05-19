package com.example.storysnap.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storysnap.data.prefs.UserModel
import com.example.storysnap.data.remote.request.LoginRequest
import com.example.storysnap.data.remote.request.RegisterRequest
import com.example.storysnap.data.repository.AppRepository
import kotlinx.coroutines.launch

class AuthViewModel (val repository: AppRepository) : ViewModel() {


    fun login(data : LoginRequest) = repository.login(data )
    fun register(data : RegisterRequest) = repository.register(data )

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}