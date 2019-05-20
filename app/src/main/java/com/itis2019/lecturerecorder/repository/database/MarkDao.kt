package com.itis2019.lecturerecorder.repository.database

import androidx.room.*
import com.itis2019.lecturerecorder.repository.dbEntities.DbMark
import io.reactivex.Flowable

@Dao
interface MarkDao {

    @Query("SELECT * FROM mark_data WHERE lectureId = :lectureId")
    fun getAll(lectureId: Long): Flowable<List<DbMark>>

    @Insert
    fun insert(mark: DbMark)

    @Update
    fun update(mark: DbMark)

    @Query("DELETE FROM mark_data WHERE lectureId = :lectureId")
    fun deleteAllLectureBinded(lectureId: Long)

    @Delete
    fun delete(mark: DbMark)
}
