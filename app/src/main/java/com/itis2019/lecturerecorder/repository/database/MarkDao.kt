package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.model.Mark
import io.reactivex.Flowable

@Dao
interface MarkDao {

    @Query("SELECT * FROM mark_data")
    fun getAll(): Flowable<List<Mark>>

    @Insert
    fun insert(mark: Mark)

    @Delete
    fun delete(mark: Mark)
}
