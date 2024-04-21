package com.example.myapplication.data.di

import com.example.myapplication.data.remote.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppApi(): ApiManager {
        return ApiManager()
    }

}