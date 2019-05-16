package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.repository.dbEntities.DbMark
import io.reactivex.Flowable

@Dao
interface MarkDao {

    @Query("SELECT * FROM mark_data")
    fun getAll(): Flowable<List<DbMark>>

    @Insert
    fun insert(mark: DbMark)

    @Delete
    fun delete(mark: DbMark)
}
