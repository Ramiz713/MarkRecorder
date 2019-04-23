package com.itis2019.lecturerecorder.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.utils.DateConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "mark_data")
data class Mark(
    @PrimaryKey
    val id: Int,
    val name: String,
    @TypeConverters(DateConverter::class)
    val time: Date,
    val lectureId: Int
) : Parcelable
