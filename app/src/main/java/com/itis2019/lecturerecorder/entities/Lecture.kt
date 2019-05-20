package com.itis2019.lecturerecorder.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Lecture(
    val id: Long = 0,
    val name: String = "",
    val duration: Long = 0,
    val creationDate: Date = Date(0),
    val filePath: String = "",
    val folderName: String = "",
    val folderBackground: Int = 0,
    val folderId: Long = 0
) : Parcelable
