package com.itis2019.lecturerecorder.service.audioPlayer

import io.reactivex.Flowable

interface AudioPlayer {

    fun pause()

    fun setDataSource(path: String)

    fun play()

    fun seekTo(progress: Long)

    fun getDuration(): Int

    fun getCurrentListeningTime(): Flowable<Int>

    fun getAudioSessionId(): Int
}
