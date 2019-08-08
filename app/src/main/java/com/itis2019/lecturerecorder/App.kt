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
        const val PLAYING_CHANNEL_ID = "playingServiceChannel"
        const val PLAYING_CHANNEL_NAME = "Playing Service Channel"
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
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val playerServiceChannel = NotificationChannel(
                PLAYING_CHANNEL_ID,
                PLAYING_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(recordingServiceChannel)
            manager.createNotificationChannel(playerServiceChannel)
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    fun goodSegment(badNumbers: Array<Int>, l: Int, r: Int): Int {
        val list = badNumbers.filter { n -> n in l..r }.toMutableList()
        list.add(l - 1)
        list.add(r + 1)
        return list.sorted()
            .zipWithNext()
            .map { z -> z.second - z.first - 1 }
            .max() ?: 0
    }
}
