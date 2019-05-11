package com.itis2019.lecturerecorder.di.module

import android.content.Context
import com.itis2019.lecturerecorder.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApp(): Context = app
}
