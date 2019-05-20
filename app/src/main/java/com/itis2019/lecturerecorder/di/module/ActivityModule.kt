package com.itis2019.lecturerecorder.di.module

import com.itis2019.lecturerecorder.ui.MainActivity
import com.itis2019.lecturerecorder.ui.folderInfo.FolderInfoActivity
import com.itis2019.lecturerecorder.ui.listener.ListeningActivity
import com.itis2019.lecturerecorder.ui.recorder.RecorderActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeRecorderActivity(): RecorderActivity

    @ContributesAndroidInjector
    abstract fun contributeListeningActivity(): ListeningActivity

    @ContributesAndroidInjector
    abstract fun contributeFolderInfoActivity(): FolderInfoActivity
}
