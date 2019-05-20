package com.itis2019.lecturerecorder.service.AudioPlayer

import io.reactivex.Flowable

interface AudioPlayer {
    fun stop()
    fun pause()
    fun playSong(path: String)
    fun play()
    fun seekTo(progress: Long)
    fun getDuration(): Int
    fun getCurrentListeningTime():Flowable<Int>
}
