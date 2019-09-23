package com.itis2019.markrecorder.repository.dbEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.itis2019.markrecorder.utils.DateConverter
import java.util.*

@Entity(tableName = "folder_data")
data class DbFolder(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @TypeConverters(DateConverter::class)
    val creationDate: Date,
    val background: Int
)
