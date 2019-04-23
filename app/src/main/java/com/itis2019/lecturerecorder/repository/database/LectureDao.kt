package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.model.Lecture

@Dao
interface LectureDao {

    @Query("SELECT * FROM lecture_data")
    fun getAll(): List<Lecture>

    @Query("SELECT * FROM lecture_data WHERE id = :id")
    fun getById(id: Int): Lecture

    @Insert
    fun insert(lecture: Lecture)

    @Query("DELETE FROM lecture_data")
    fun deleteAll()
}
