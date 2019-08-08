package com.itis2019.lecturerecorder.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis2019.lecturerecorder.repository.dbEntities.DbFolder
import com.itis2019.lecturerecorder.repository.dbEntities.DbLecture
import com.itis2019.lecturerecorder.utils.DateConverter
import com.itis2019.lecturerecorder.utils.MarksConverter

@Database(entities = [DbLecture::class, DbFolder::class], version = 1)
@TypeConverters(DateConverter::class, MarksConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lectureDao(): LectureDao

    abstract fun folderDao(): FolderDao
}
