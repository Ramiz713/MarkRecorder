package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.repository.converters.convertToDbLecture
import com.itis2019.lecturerecorder.repository.converters.convertToLecture
import com.itis2019.lecturerecorder.repository.database.LectureDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LectureRepositoryImpl(private val lectureDao: LectureDao) : LectureRepository {

    override fun getAllLectures(): Observable<List<Lecture>> =
        lectureDao.getAll()
            .map { list -> list.map { it.convertToLecture() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getLectures(folderId: Long): Observable<List<Lecture>> =
        lectureDao.getLectures(folderId)
            .map { list -> list.map { it.convertToLecture() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getLecture(id: Long): Single<Lecture> =
        lectureDao.getById(id)
            .map { it.convertToLecture() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun updateLecture(lecture: Lecture): Observable<Unit> =
        Observable.fromCallable { lectureDao.updateLecture(lecture.convertToDbLecture()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertLecture(lecture: Lecture): Observable<Long> =
        Observable.fromCallable { lectureDao.insert(lecture.convertToDbLecture()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteLecture(lecture: Lecture): Observable<Unit> =
        Observable.fromCallable { lectureDao.delete(lecture.convertToDbLecture()) }
            .subscribeOn(Schedulers.io())
}
