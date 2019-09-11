package com.itis2019.lecturerecorder.utils

import android.content.Context
import android.text.TextUtils
import android.widget.EditText
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentActivity
import com.itis2019.lecturerecorder.R
import java.io.File
import java.util.concurrent.TimeUnit

const val ITEM_VIEW_TYPE_HEADER = 0
const val ITEM_VIEW_TYPE_ITEM = 1
const val ITEM_VIEW_TYPE_ITEM_ANOTHER_STYLE = 2
const val ITEM_VIEW_TYPE_FOLDER = 3

fun Context.getFromHtml(stringId: Int, string: String) =
    HtmlCompat.fromHtml(
        this.getString(stringId, string),
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )

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

fun deleteFile(path: String) {
    File(path).delete()
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

fun FragmentActivity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

