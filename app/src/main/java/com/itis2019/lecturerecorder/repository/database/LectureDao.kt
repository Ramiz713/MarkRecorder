package com.itis2019.lecturerecorder.repository.database

import androidx.room.*
import com.itis2019.lecturerecorder.repository.dbEntities.DbLecture
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface LectureDao {

    @Query("SELECT * FROM lecture_data ORDER BY creationDate DESC")
    fun getAll(): Flowable<List<DbLecture>>

    @Query("SELECT * FROM lecture_data WHERE folderId = :folderId ORDER BY creationDate DESC ")
    fun getLectures(folderId: Long): Flowable<List<DbLecture>>

    @Query("SELECT * FROM lecture_data WHERE id = :id")
    fun getById(id: Long): Single<DbLecture>

    @Update
    fun updateLecture(lecture: DbLecture)

    @Insert
    fun insert(lecture: DbLecture): Long

    @Delete
    fun delete(lecture: DbLecture)
}
