package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.model.Folder
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FolderDao {

    @Query("SELECT * FROM folder_data ORDER BY creationDate DESC")
    fun getAll(): Flowable<List<Folder>>

    @Query("SELECT * FROM folder_data WHERE id = :id")
    fun getById(id: Int): Single<Folder>

    @Insert
    fun insert(folder: Folder)

    @Delete
    fun delete(folder: Folder)
}
