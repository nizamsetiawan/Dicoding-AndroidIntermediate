package com.example.storysnap.data.prefs

data class UserModel(
    var email: String,
    var password: String,
    val token: String,
    val isLogin: Boolean = false
)