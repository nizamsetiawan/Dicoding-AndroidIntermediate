package com.example.storysnap.data.remote.response


import com.google.gson.annotations.SerializedName

data class AddNewStoryResponse(
    @SerializedName("error")
    var error: Boolean?,
    @SerializedName("message")
    var message: String?
)