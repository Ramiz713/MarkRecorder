package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.entities.Mark
import io.reactivex.Flowable
import io.reactivex.Observable

interface MarkRepository {

    fun getLectureMarks(lectureId: Long): Flowable<List<Mark>>

    fun insertMark(mark: Mark): Observable<Unit>

    fun updateMark(mark: Mark): Observable<Unit>

    fun deleteAllLectureBindedMarks(lectureId: Long): Observable<Unit>

    fun deleteMark(mark: Mark): Observable<Unit>
}
