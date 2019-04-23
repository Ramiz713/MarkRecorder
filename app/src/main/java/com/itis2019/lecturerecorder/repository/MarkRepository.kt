package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.model.Mark
import io.reactivex.Single

interface MarkRepository {
    fun insertMark(mark: Mark)
    fun deleteMark(mark: Mark)
    fun getAllMark(): Single<List<Mark>>
}
