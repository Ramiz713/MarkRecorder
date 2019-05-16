package com.itis2019.lecturerecorder.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Lecture(
    val id: Int,
    val name: String,
    val duration: Long,
    val creationDate: Date,
    val filePath: String,
    val folderName: String,
    val folderBackground: Int,
    val folderId: Int
) : Parcelable
