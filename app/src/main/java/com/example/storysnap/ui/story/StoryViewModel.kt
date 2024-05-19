package com.example.storysnap.ui.story

import androidx.lifecycle.ViewModel
import com.example.storysnap.data.repository.AppRepository
import java.io.File

class StoryViewModel(val repository: AppRepository) : ViewModel() {

    fun uploadStory(token: String, fileImage: File, description: String) = repository.addNewStory(token, fileImage, description)


}