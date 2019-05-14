package com.itis2019.lecturerecorder.service

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import com.itis2019.lecturerecorder.utils.FileSaverHelpers.Companion.updateWavHeader
import com.itis2019.lecturerecorder.utils.FileSaverHelpers.Companion.writeWavHeader
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.io.File
import java.io.FileOutputStream

class AudioRecorderImpl : AudioRecorder {

    private var isRecording = true

    companion object {
        private const val SAMPLE_RATE = 44100
        private const val ENCODING = AudioFormat.ENCODING_PCM_16BIT
        private const val CHANNEL = AudioFormat.CHANNEL_IN_MONO
    }

    private var bufferSize = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        CHANNEL,
        ENCODING
    )

    private val audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        SAMPLE_RATE,
        CHANNEL,
        ENCODING,
        bufferSize
    )

    private lateinit var file: File
    private lateinit var os: FileOutputStream

    private val disposables = CompositeDisposable()

    private val dataInBytes: Flowable<ByteArray> =
        Flowable.create({ emitter ->
            audioRecord.startRecording()
            while (isRecording) {
                val recordBuffer = ByteArray(bufferSize)
                val bytes = audioRecord.read(recordBuffer, 0, bufferSize)
                if (bytes == 0)
                    break
                emitter.onNext(recordBuffer)
            }
            audioRecord.stop()
        }, BackpressureStrategy.DROP)

    private val recordDataPublishProcessor = PublishProcessor.create<ByteArray>()

    override fun startRecord() {
        isRecording = true
        audioRecord.startRecording()
        createNewFile()
        dataInBytes.subscribeOn(Schedulers.io()).subscribe(recordDataPublishProcessor)

        disposables.add(recordDataPublishProcessor.onBackpressureBuffer()
            .observeOn(Schedulers.io())
            .subscribeWith(object : DisposableSubscriber<ByteArray>() {
                override fun onComplete() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(t: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ByteArray?) {
                    t?.let { writeData(it) }
                }
            })
        )
    }

    private fun createNewFile() {
        val storeLocation = Environment.getExternalStorageDirectory().absolutePath
        val folder = File("$storeLocation/LectureRecorder")
        if (!folder.exists())
            folder.mkdir()
        val count = folder.listFiles().count() + 1
        val fileName: String

        fileName = "AudioRecord_$count.wav"
        val filePath = "$storeLocation/LectureRecorder/$fileName"
        file = File(filePath)

        os = FileOutputStream(file)
        writeWavHeader(
            os, CHANNEL, SAMPLE_RATE,
            ENCODING
        )
    }

    private fun writeData(data: ByteArray) {
        os.write(data, 0, data.size)
    }

    override fun pauseRecord() {
        isRecording = false
    }

    override fun resumeRecord() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun finishRecord(): String {
        isRecording = false
        disposables.clear()
        onRecordingStopped()
        return file.absolutePath
    }

    override fun getRawBytes(): Flowable<ByteArray> =
        dataInBytes.map { buffer -> processRawBytesForVisualizer(buffer) }

    private fun processRawBytesForVisualizer(buffer: ByteArray): ByteArray {
        val bufferForVisualize = ByteArray(512)
        var tempCounter = 0
        val audioLength = (bufferSize * 1000F / SAMPLE_RATE).toInt()

        for (idx in 0 until bufferSize step (bufferSize / (audioLength + bufferForVisualize.size))) {
            if (tempCounter >= bufferForVisualize.size) {
                break
            }
            bufferForVisualize[tempCounter++] = buffer[idx]
        }
        return bufferForVisualize
    }

    override fun getTime(): Flowable<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRecording(): Boolean = isRecording

    private fun onRecordingStopped() {
            os.close()
            updateWavHeader(file)
    }
}
