package com.itis2019.lecturerecorder.utils

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}
