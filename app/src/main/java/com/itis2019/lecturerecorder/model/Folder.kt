package com.itis2019.lecturerecorder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folder_data")
data class Folder(
    @PrimaryKey
    val id: Int,
    val name: String,
    val backgroundColor: Int
)
