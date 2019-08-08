package com.itis2019.lecturerecorder.service.AudioRecording

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import com.itis2019.lecturerecorder.utils.updateWavHeader
import com.itis2019.lecturerecorder.utils.writeWavHeader
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class AudioRecorderImpl : AudioRecorder {

    private var isRecording = false
    private var isPaused = false

    private var totalTime: Long = 0

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
    private lateinit var fileOutput: FileOutputStream

    private val disposables = CompositeDisposable()

    override fun getRawBytes(): Flowable<ByteArray> =
        recordDataPublishProcessor

    override fun getTime(): Flowable<Long> = timer

    private val timer: Flowable<Long> =
        Flowable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io())
            .filter { isRecording }
            .filter { !isPaused }
            .map {
                totalTime += 1000
                return@map totalTime
            }

    private val dataInBytes: Flowable<ByteArray> =
        Flowable.create({ emitter ->
            while (isRecording) {
                if (!isPaused) {
                    val recordBuffer = ByteArray(bufferSize)
                    audioRecord.read(recordBuffer, 0, bufferSize)
                    emitter.onNext(recordBuffer)
                }
            }
            audioRecord.release()
            emitter.onComplete()
        }, BackpressureStrategy.DROP)

    private val recordDataPublishProcessor = PublishProcessor.create<ByteArray>()

    override fun startRecord() {
        isRecording = true
        audioRecord.startRecording()
        createNewFile()
        dataInBytes.subscribeOn(Schedulers.io()).subscribe(recordDataPublishProcessor)

        disposables.add(
            recordDataPublishProcessor.onBackpressureBuffer()
                .observeOn(Schedulers.io())
                .subscribe { writeData(it) })
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

        fileOutput = FileOutputStream(file)
        writeWavHeader(fileOutput, CHANNEL, SAMPLE_RATE, ENCODING)
    }

    private fun writeData(data: ByteArray) {
        fileOutput.write(data, 0, data.size)
    }

    override fun pauseRecord() {
        isPaused = true
        audioRecord.stop()
    }

    override fun resumeRecord() {
        audioRecord.startRecording()
        isPaused = false
    }

    override fun finishRecordWithSaving(): String {
        finishRecording()
        updateWavHeader(file)
        return file.absolutePath
    }

    override fun finishRecordWithoutSaving() {
        finishRecording()
        if (::file.isInitialized) file.delete()
    }

    private fun finishRecording() {
        isRecording = false
        totalTime = 0
        audioRecord.release()
        if (::fileOutput.isInitialized) fileOutput.close()
    }
}
