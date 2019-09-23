package com.itis2019.lecturerecorder.service.audioPlayer

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.itis2019.lecturerecorder.App
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.ui.MainActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class AudioPlayerService : Service(), AudioPlayer {

    private val binder = AudioPlayerBinder()

    inner class AudioPlayerBinder : Binder() {
        fun getService(): AudioPlayerService = this@AudioPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intent = Intent(this, MainActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, App.PLAYING_CHANNEL_ID)
            .setContentTitle("Playing...")
            .setSound(null)
            .setColor(this.resources.getColor(R.color.colorPrimary))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        stop()
        stopForeground(true)
    }

    private lateinit var mediaPlayer: MediaPlayer

    private var isNotStopped = true
    private var isNotPaused = false

    override fun pause() {
        mediaPlayer.pause()
//        isNotPaused = false
    }

    override fun setDataSource(path: String) {
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare()
        mediaPlayer.isLooping = false
    }

    override fun play() {
        mediaPlayer.start()
        isNotStopped = true
        isNotPaused = true
    }

    override fun seekTo(progress: Long) {
        mediaPlayer.seekTo(progress.toInt())
    }

    override fun getCurrentListeningTime(): Flowable<Int> =
        Flowable.create({ emitter ->
            while (isNotStopped) {
                if (mediaPlayer.isPlaying) {
                    val time = mediaPlayer.currentPosition
                    emitter.onNext(time)
                }
            }
            mediaPlayer.release()
            emitter.onComplete()
        }, BackpressureStrategy.DROP)

    override fun getDuration(): Int = mediaPlayer.duration

    override fun getAudioSessionId(): Int = mediaPlayer.audioSessionId

    private fun stop() {
        isNotStopped = false
        mediaPlayer.stop()
    }
}
