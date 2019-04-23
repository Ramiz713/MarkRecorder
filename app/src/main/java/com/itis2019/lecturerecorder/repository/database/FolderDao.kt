package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.model.Folder

@Dao
interface FolderDao {
    @Query("SELECT * FROM folder_data")
    fun getAll(): List<Folder>

    @Query("SELECT * FROM folder_data WHERE id = :id")
    fun getById(id: Int): Folder

    @Insert
    fun insert(folder: Folder)

    @Delete
    fun delete(folder: Folder)
}
