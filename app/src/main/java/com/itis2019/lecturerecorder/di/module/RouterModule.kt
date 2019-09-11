package com.itis2019.lecturerecorder.di.module

import com.itis2019.lecturerecorder.router.Router
import com.itis2019.lecturerecorder.router.RouterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RouterModule {

    @Provides
    @Singleton
    fun provideRouter(): Router = RouterImpl()
}
