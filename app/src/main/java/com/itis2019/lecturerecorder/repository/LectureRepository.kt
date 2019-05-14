package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.model.Lecture
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface LectureRepository {

    fun getAllLectures(): Flowable<List<Lecture>>

    fun getLecture(id: Int): Single<Lecture>

    fun updateLecture(lecture: Lecture): Observable<Unit>

    fun insertLecture(lecture: Lecture): Observable<Unit>

    fun deleteLecture(lecture: Lecture): Observable<Unit>
}
