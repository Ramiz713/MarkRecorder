package com.itis2019.lecturerecorder

import android.app.Activity
import android.app.Application
import com.itis2019.lecturerecorder.di.DaggerAppComponent
import com.itis2019.lecturerecorder.utils.dagger.autoInject
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        autoInject()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
