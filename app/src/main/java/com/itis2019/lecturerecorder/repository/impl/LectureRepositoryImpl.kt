package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.repository.database.LectureDao
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.utils.subscribeObservableOnIoObserveOnUi
import com.itis2019.lecturerecorder.utils.subscribeSingleOnIoObserveOnUi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class LectureRepositoryImpl(private val lectureDao: LectureDao) : LectureRepository {

    override fun getAllLectures(): Flowable<List<Lecture>> =
            lectureDao.getAll().observeOn(AndroidSchedulers.mainThread())

    override fun getLecture(id: Int): Single<Lecture> =
            lectureDao.getById(id).subscribeSingleOnIoObserveOnUi()

    override fun insertLecture(lecture: Lecture) {
        Observable.fromCallable { lectureDao.delete(lecture) }
                .subscribeObservableOnIoObserveOnUi()
                .subscribe()
    }

    override fun deleteLecture(lecture: Lecture) {
        Observable.fromCallable { lectureDao.delete(lecture) }
                .subscribeObservableOnIoObserveOnUi()
                .subscribe()
    }
}
