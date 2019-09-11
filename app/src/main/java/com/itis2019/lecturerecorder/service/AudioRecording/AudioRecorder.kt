package com.itis2019.lecturerecorder.service.AudioRecording

import io.reactivex.Flowable

interface AudioRecorder  {

    fun startRecord()

    fun finishRecordWithSaving(): String

    fun finishRecordWithoutSaving()

    fun pauseRecord()

    fun resumeRecord()

    fun getRawBytes(): Flowable<ByteArray>

    fun getTime(): Flowable<Long>
}
