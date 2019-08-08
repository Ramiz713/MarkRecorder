package com.itis2019.lecturerecorder.repository.dbEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.utils.DateConverter
import com.itis2019.lecturerecorder.utils.MarksConverter
import java.util.*

@Entity(tableName = "lecture_data")
data class DbLecture(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @TypeConverters(MarksConverter::class)
    val marks: List<DbMark>,
    val duration: Long,
    @TypeConverters(DateConverter::class)
    val creationDate: Date,
    val filePath: String,
    val folderName: String,
    val folderBackground: Int,
    val folderId: Long
)
