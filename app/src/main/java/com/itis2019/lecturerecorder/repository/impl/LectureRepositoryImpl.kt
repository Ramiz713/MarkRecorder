package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.repository.converters.convertToDbLecture
import com.itis2019.lecturerecorder.repository.converters.convertToLecture
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LectureRepositoryImpl(private val lectureDao: LectureDao) : LectureRepository {

    override fun getAllLectures(): Flowable<List<Lecture>> =
        lectureDao.getAll()
            .map { list -> list.map { it.convertToDbLecture() } }
            .observeOn(AndroidSchedulers.mainThread())

    override fun getLecture(id: Int): Single<Lecture> =
        lectureDao.getById(id)
            .map { it.convertToDbLecture() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun updateLecture(lecture: Lecture): Observable<Unit> =
        Observable.fromCallable { lectureDao.updateLecture(lecture.convertToLecture()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertLecture(lecture: Lecture): Observable<Long> =
        Observable.fromCallable { lectureDao.insert(lecture.convertToLecture()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteLecture(lecture: Lecture): Observable<Unit> =
        Observable.fromCallable { lectureDao.delete(lecture.convertToLecture()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
