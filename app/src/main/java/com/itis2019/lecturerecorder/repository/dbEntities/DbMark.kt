package com.itis2019.lecturerecorder.repository.dbEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.utils.DateConverter

@Entity(tableName = "mark_data")
data class DbMark(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @TypeConverters(DateConverter::class)
    val time: Long,
    val lectureId: Long
)
