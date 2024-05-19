package com.example.storysnap.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storysnap.data.remote.response.ListStoryItem
import com.example.storysnap.data.repository.AppRepository

class HomeViewModel(val repository: AppRepository) : ViewModel() {

    val detail = repository.detail
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> = repository.getStories(token).cachedIn(viewModelScope)
    fun getDetail(token: String, id:String) = repository.getDetailStories(token, id)


}