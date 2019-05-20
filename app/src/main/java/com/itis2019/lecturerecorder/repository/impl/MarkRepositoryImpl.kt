package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.repository.database.MarkDao
import com.itis2019.lecturerecorder.repository.MarkRepository
import com.itis2019.lecturerecorder.repository.converters.convertToMark
import com.itis2019.lecturerecorder.repository.converters.convertToDbMark
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MarkRepositoryImpl(private val markDao: MarkDao) : MarkRepository {

    override fun getLectureMarks(lectureId: Long): Flowable<List<Mark>> =
        markDao.getAll(lectureId)
            .map { list -> list.map { it.convertToMark() } }
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertMark(mark: Mark): Observable<Unit> =
        Observable.fromCallable { markDao.insert(mark.convertToDbMark()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun updateMark(mark: Mark): Observable<Unit> =
        Observable.fromCallable { markDao.update(mark.convertToDbMark()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteAllLectureBindedMarks(lectureId: Long) =
        Observable.fromCallable {
            markDao.deleteAllLectureBinded(lectureId)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteMark(mark: Mark): Observable<Unit> {
        return Observable.fromCallable { markDao.delete(mark.convertToDbMark()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
