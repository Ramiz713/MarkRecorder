package com.itis2019.lecturerecorder.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itis2019.lecturerecorder.model.Lecture
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface LectureDao {

    @Query("SELECT * FROM lecture_data ORDER BY creationDate DESC")
    fun getAll(): Flowable<List<Lecture>>

    @Query("SELECT * FROM lecture_data WHERE id = :id")
    fun getById(id: Int): Single<Lecture>

    @Insert
    fun insert(lecture: Lecture)

    @Delete
    fun delete(lecture: Lecture)
}
