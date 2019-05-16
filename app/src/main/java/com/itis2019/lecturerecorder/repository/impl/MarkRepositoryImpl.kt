package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.repository.database.MarkDao
import com.itis2019.lecturerecorder.repository.MarkRepository
import com.itis2019.lecturerecorder.repository.converters.convertToMark
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MarkRepositoryImpl(private val markDao: MarkDao) : MarkRepository {

    override fun getLectureMarks(lectureId: Int): Flowable<List<Mark>> =
        markDao.getAll()
            .map { list -> list.map { it.convertToMark() } }
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertMark(mark: Mark): Observable<Unit> =
        Observable.fromCallable { markDao.insert(mark.convertToMark()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteMark(mark: Mark): Observable<Unit> =
        Observable.fromCallable { markDao.delete(mark.convertToMark()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
