package com.itis2019.lecturerecorder

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.itis2019.lecturerecorder.di.DaggerAppComponent
import com.itis2019.lecturerecorder.utils.dagger.autoInject
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    companion object {
        const val RECORDING_CHANNEL_ID = "recordingServiceChannel"
        const val RECORDING_CHANNEL_NAME = "Recording Service Channel"
        val PLAYING_CHANNEL_ID = "playingServiceChannel"
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        autoInject()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val recordingServiceChannel = NotificationChannel(
                RECORDING_CHANNEL_ID,
                RECORDING_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(recordingServiceChannel)
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
