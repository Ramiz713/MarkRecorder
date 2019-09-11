package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.repository.converters.convertToDbRecord
import com.itis2019.lecturerecorder.repository.converters.convertToRecord
import com.itis2019.lecturerecorder.repository.database.RecordDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RecordRepositoryImpl(private val recordDao: RecordDao) : RecordRepository {

    override fun getAllRecords(): Observable<List<Record>> =
        recordDao.getAll()
            .map { list -> list.map { it.convertToRecord() } }

    override fun getRecordsFromFolder(folderId: Long): Observable<List<Record>> =
        recordDao.getLectures(folderId)
            .map { list -> list.map { it.convertToRecord() } }
            .subscribeOn(Schedulers.io())

    override fun getRecord(id: Long): Single<Record> =
        recordDao.getById(id)
            .map { it.convertToRecord() }
            .subscribeOn(Schedulers.io())

    override fun updateRecord(lecture: Record): Observable<Unit> =
        Observable.fromCallable { recordDao.updateLecture(lecture.convertToDbRecord()) }
            .subscribeOn(Schedulers.io())

    override fun insertRecord(lecture: Record): Observable<Long> =
        Observable.fromCallable { recordDao.insert(lecture.convertToDbRecord()) }
            .subscribeOn(Schedulers.io())

    override fun deleteRecord(lecture: Record): Observable<Unit> =
        Observable.fromCallable { recordDao.delete(lecture.convertToDbRecord()) }
            .subscribeOn(Schedulers.io())
}
