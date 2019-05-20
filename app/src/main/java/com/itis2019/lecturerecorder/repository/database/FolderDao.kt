package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.repository.dbEntities.DbFolder
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FolderDao {

    @Query("SELECT * FROM folder_data ORDER BY creationDate DESC")
    fun getAll(): Flowable<List<DbFolder>>

    @Query("SELECT * FROM folder_data WHERE id = :id")
    fun getById(id: Long): Single<DbFolder>

    @Insert
    fun insert(folder: DbFolder) : Long

    @Delete
    fun delete(folder: DbFolder)
}
