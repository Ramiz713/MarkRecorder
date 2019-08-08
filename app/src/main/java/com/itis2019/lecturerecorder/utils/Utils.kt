package com.itis2019.lecturerecorder.utils

import android.text.TextUtils
import android.widget.EditText
import com.itis2019.lecturerecorder.R
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

fun getRandomGradientColor(): Int = listOf(
    R.drawable.gradient_blue,
    R.drawable.gradient_dark_skies,
    R.drawable.gradient_green,
    R.drawable.gradient_grey,
    R.drawable.gradient_orange,
    R.drawable.gradient_purple,
    R.drawable.gradient_red,
    R.drawable.gradient_yellow
).random()

