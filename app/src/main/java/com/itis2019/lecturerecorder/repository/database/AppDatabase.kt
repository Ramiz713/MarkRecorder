package com.itis2019.lecturerecorder.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.repository.dbEntities.DbFolder
import com.itis2019.lecturerecorder.repository.dbEntities.DbRecord
import com.itis2019.lecturerecorder.utils.DateConverter
import com.itis2019.lecturerecorder.utils.MarksConverter

@Database(entities = [DbRecord::class, DbFolder::class], version = 1)
@TypeConverters(DateConverter::class, MarksConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lectureDao(): RecordDao

    abstract fun folderDao(): FolderDao
}
