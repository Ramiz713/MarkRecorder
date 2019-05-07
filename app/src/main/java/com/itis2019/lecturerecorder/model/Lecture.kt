package com.itis2019.lecturerecorder.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.utils.DateConverter
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "lecture_data")
data class Lecture(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val name: String,
        val duration: Int,
        @TypeConverters(DateConverter::class)
        val creationDate: Date,
        val folderName: String,
        val folderBackground: Int,
        val folderId: Int
) : Parcelable
