package com.example.storysnap.di

import com.example.storysnap.data.prefs.UserPreference
import com.example.storysnap.data.prefs.dataStore
import com.example.storysnap.data.remote.RemoteDataSource
import com.example.storysnap.data.remote.network.ApiConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { ApiConfig.provideApiService }
    single { RemoteDataSource(get()) }
    single { UserPreference(androidContext().dataStore) }

}
