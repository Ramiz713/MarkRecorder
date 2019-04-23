package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.model.Lecture
import io.reactivex.Single

interface LectureRepository {
    fun getLecture(id: Int)
    fun insertLecture(lecture: Lecture)
    fun deleteLecture(lecture: Lecture)
    fun getAllLectures(): Single<List<Lecture>>
}
