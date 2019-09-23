package com.itis2019.lecturerecorder.repository.dbEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.utils.DateConverter
import com.itis2019.lecturerecorder.utils.MarksConverter
import java.util.*

@Entity(tableName = "record_data")
data class DbRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @TypeConverters(MarksConverter::class)
    val marks: List<DbMark>,
    val duration: Long,
    @TypeConverters(DateConverter::class)
    val creationDate: Date,
    val filePath: String,
    val folderId: Long
)
