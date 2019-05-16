package com.itis2019.lecturerecorder.utils

import java.util.concurrent.TimeUnit

fun getTimeInFormatWithSeconds(millis: Long): String =
    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))
