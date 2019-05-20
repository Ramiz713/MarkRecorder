package com.itis2019.lecturerecorder.utils

import android.text.TextUtils
import android.widget.EditText
import java.util.concurrent.TimeUnit

fun getTimeInFormatWithSeconds(millis: Long): String =
    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))

fun validateEditText(editText: EditText, text: String): Boolean {
    if (TextUtils.isEmpty(text)) {
        editText.error = "Cannot be empty!"
        return false
    }
    return true
}
