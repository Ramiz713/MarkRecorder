package com.itis2019.markrecorder.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Folder(
    val id: Long,
    val name: String,
    val creationDate: Date,
    val background: Int
): Parcelable
