package com.itis2019.lecturerecorder.repository.database

import androidx.room.*
import com.itis2019.lecturerecorder.repository.dbEntities.DbFolder
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FolderDao {

    @Query("SELECT * FROM folder_data ORDER BY creationDate DESC")
    fun getAll(): Observable<List<DbFolder>>

    @Query("SELECT * FROM folder_data WHERE id = :id")
    fun getById(id: Long): Single<DbFolder>

    @Update
    fun update(folder: DbFolder)

    @Insert
    fun insert(folder: DbFolder): Long

    @Delete
    fun delete(folder: DbFolder)
}
