package com.example.storysnap.utils

import android.app.Application
import com.example.storysnap.di.appModule
import com.example.storysnap.di.repositoryModule
import com.example.storysnap.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                appModule
            ))
        }
    }
}