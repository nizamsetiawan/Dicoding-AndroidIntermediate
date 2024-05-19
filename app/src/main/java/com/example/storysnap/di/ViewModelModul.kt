package com.example.storysnap.di

import com.example.storysnap.ui.auth.AuthViewModel
import com.example.storysnap.ui.home.HomeViewModel
import com.example.storysnap.ui.setting.SettingViewModel
import com.example.storysnap.ui.story.StoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { StoryViewModel(get()) }
    viewModel { SettingViewModel(get()) }

}