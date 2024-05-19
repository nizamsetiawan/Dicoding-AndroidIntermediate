package com.example.storysnap.ui.setting

import androidx.lifecycle.ViewModel
import com.example.storysnap.data.repository.AppRepository

class SettingViewModel(val repository: AppRepository) : ViewModel() {
    val storyLocation = repository.storyLocation
    fun getStoryLocation(token : String) = repository.getStoriesWithLocation(token)
}