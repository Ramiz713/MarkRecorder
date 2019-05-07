package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.model.Mark
import io.reactivex.Flowable
import io.reactivex.Observable

interface MarkRepository {

    fun getLectureMarks(lectureId: Int): Flowable<List<Mark>>

    fun insertMark(mark: Mark): Observable<Unit>

    fun deleteMark(mark: Mark): Observable<Unit>
}
