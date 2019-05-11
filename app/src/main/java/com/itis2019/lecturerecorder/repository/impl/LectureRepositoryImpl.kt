package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.LectureRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LectureRepositoryImpl(private val lectureDao: LectureDao) : LectureRepository {

    override fun getAllLectures(): Flowable<List<Lecture>> =
        lectureDao.getAll().observeOn(AndroidSchedulers.mainThread())

    override fun getLecture(id: Int): Single<Lecture> =
        lectureDao.getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertLecture(lecture: Lecture): Observable<Unit> =
        Observable.fromCallable { lectureDao.delete(lecture) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteLecture(lecture: Lecture): Observable<Unit> =
        Observable.fromCallable { lectureDao.delete(lecture) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
