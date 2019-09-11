package com.itis2019.lecturerecorder.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mark(
    val id: Int,
    val name: String,
    val time: Long
) : Parcelable
