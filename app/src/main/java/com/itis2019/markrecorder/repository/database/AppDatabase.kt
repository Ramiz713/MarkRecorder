package com.itis2019.markrecorder.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis2019.markrecorder.repository.dbEntities.DbFolder
import com.itis2019.markrecorder.repository.dbEntities.DbRecord
import com.itis2019.markrecorder.utils.DateConverter
import com.itis2019.markrecorder.utils.MarksConverter

@Database(entities = [DbRecord::class, DbFolder::class], version = 1)
@TypeConverters(DateConverter::class, MarksConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    abstract fun folderDao(): FolderDao
}
