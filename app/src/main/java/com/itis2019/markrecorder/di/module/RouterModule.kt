package com.itis2019.markrecorder.di.module

import com.itis2019.markrecorder.router.Router
import com.itis2019.markrecorder.router.RouterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RouterModule {

    @Provides
    @Singleton
    fun provideRouter(): Router = RouterImpl()
}
