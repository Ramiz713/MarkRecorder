package com.itis2019.lecturerecorder.service

import io.reactivex.Flowable

interface AudioRecorder  {

    abstract fun startRecord()

    abstract fun finishRecord(): String

    abstract fun pauseRecord()

    abstract fun resumeRecord()

    abstract fun getRawBytes() : Flowable<ByteArray>

    abstract fun getTime() : Flowable<Long>

    abstract fun isRecording(): Boolean
}
