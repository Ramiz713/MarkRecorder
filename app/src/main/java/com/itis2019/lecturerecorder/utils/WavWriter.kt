package com.itis2019.lecturerecorder.utils

import android.media.AudioFormat
import java.io.File
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun updateWavHeader(wav: File) {
    val sizes = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
        .putInt((wav.length() - 8).toInt())
        .putInt((wav.length() - 44).toInt())
        .array()

    val accessWave = RandomAccessFile(wav, "rw")
    accessWave.seek(4)
    accessWave.write(sizes, 0, 4)
    accessWave.seek(40)
    accessWave.write(sizes, 4, 4)
}

fun writeWavHeader(out: OutputStream, channelMask: Int, sampleRate: Int, encoding: Int) {
    val channels: Short = when (channelMask) {
        AudioFormat.CHANNEL_IN_MONO -> 1
        AudioFormat.CHANNEL_IN_STEREO -> 2
        else -> throw IllegalArgumentException("Unacceptable channel mask")
    }

    val bitDepth: Short = when (encoding) {
        AudioFormat.ENCODING_PCM_8BIT -> 8
        AudioFormat.ENCODING_PCM_16BIT -> 16
        AudioFormat.ENCODING_PCM_FLOAT -> 32
        else -> throw IllegalArgumentException("Unacceptable encoding")
    }

    writeWavHeader(out, channels, sampleRate, bitDepth)
}

fun writeWavHeader(out: OutputStream, channels: Short, sampleRate: Int, bitDepth: Short) {
    val littleBytes = ByteBuffer.allocate(14)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putShort(channels)
        .putInt(sampleRate)
        .putInt(sampleRate * channels.toInt() * (bitDepth / 8))
        .putShort((channels * (bitDepth / 8)).toShort())
        .putShort(bitDepth)
        .array()

    out.write(
        byteArrayOf(
            'R'.toByte(), 'I'.toByte(), 'F'.toByte(), 'F'.toByte(),
            0, 0, 0, 0,
            'W'.toByte(), 'A'.toByte(), 'V'.toByte(), 'E'.toByte(),
            'f'.toByte(), 'm'.toByte(), 't'.toByte(), ' '.toByte(),
            16, 0, 0, 0,
            1, 0,
            littleBytes[0], littleBytes[1],
            littleBytes[2], littleBytes[3], littleBytes[4], littleBytes[5],
            littleBytes[6], littleBytes[7], littleBytes[8], littleBytes[9],
            littleBytes[10], littleBytes[11],
            littleBytes[12], littleBytes[13],
            'd'.toByte(), 'a'.toByte(), 't'.toByte(), 'a'.toByte(),
            0, 0, 0, 0
        )
    )
}
