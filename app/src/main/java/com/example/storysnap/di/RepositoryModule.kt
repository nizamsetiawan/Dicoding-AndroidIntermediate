package com.example.storysnap.di

import com.example.storysnap.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(), get()) } }
