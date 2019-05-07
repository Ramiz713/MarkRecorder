package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.model.Mark
import io.reactivex.Flowable

interface MarkRepository {

    fun getLectureMarks(lectureId: Int): Flowable<List<Mark>>

    fun insertMark(mark: Mark)

    fun deleteMark(mark: Mark)
}
