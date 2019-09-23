package com.itis2019.lecturerecorder.repository.database

import androidx.room.*
import com.itis2019.lecturerecorder.repository.dbEntities.DbRecord
import com.itis2019.lecturerecorder.repository.dbEntities.DbRecordWithFolder
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RecordDao {

    @Query("SELECT * FROM record_data ORDER BY creationDate DESC")
    fun getAll(): Observable<List<DbRecordWithFolder>>

    @Query("SELECT * FROM record_data WHERE folderId = :folderId ORDER BY creationDate DESC ")
    fun getRecordsByFolderId(folderId: Long): Observable<List<DbRecord>>

    @Query("SELECT * FROM record_data WHERE id = :id")
    fun getById(id: Long): Single<DbRecord>

    @Update
    fun update(record: DbRecord)

    @Insert
    fun insert(record: DbRecord): Long

    @Delete
    fun delete(record: DbRecord)
}
