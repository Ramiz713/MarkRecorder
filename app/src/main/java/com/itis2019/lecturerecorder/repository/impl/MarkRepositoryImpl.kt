package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Mark
import com.itis2019.lecturerecorder.repository.database.MarkDao
import com.itis2019.lecturerecorder.repository.MarkRepository
import com.itis2019.lecturerecorder.utils.subscribeObservableOnIoObserveOnUi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MarkRepositoryImpl(private val markDao: MarkDao) : MarkRepository {

    override fun getLectureMarks(lectureId: Int): Flowable<List<Mark>> =
            markDao.getAll().observeOn(AndroidSchedulers.mainThread())

    override fun insertMark(mark: Mark) {
        Observable.fromCallable { markDao.insert(mark) }
                .subscribeObservableOnIoObserveOnUi()
                .subscribe()
    }

    override fun deleteMark(mark: Mark) {
        Observable.fromCallable { markDao.delete(mark) }
                .subscribeObservableOnIoObserveOnUi()
                .subscribe()
    }
}
