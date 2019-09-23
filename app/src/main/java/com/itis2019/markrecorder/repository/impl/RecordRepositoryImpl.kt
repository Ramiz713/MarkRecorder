package com.itis2019.markrecorder.repository.impl

import com.itis2019.markrecorder.entities.Record
import com.itis2019.markrecorder.repository.RecordRepository
import com.itis2019.markrecorder.repository.converters.convertToDbRecord
import com.itis2019.markrecorder.repository.converters.convertToRecord
import com.itis2019.markrecorder.repository.database.RecordDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RecordRepositoryImpl(private val recordDao: RecordDao) : RecordRepository {

    override fun getAllRecords(): Observable<List<Record>> =
        recordDao.getAll()
            .map { list -> list.map { it.convertToRecord() } }

    override fun getRecordsFromFolder(folderId: Long): Observable<List<Record>> =
        recordDao.getRecordsByFolderId(folderId)
            .map { list -> list.map { it.convertToRecord() } }

    override fun getRecord(id: Long): Single<Record> =
        recordDao.getById(id)
            .map { it.convertToRecord() }
            .subscribeOn(Schedulers.io())

    override fun updateRecord(record: Record): Observable<Unit> =
        Observable.fromCallable { recordDao.update(record.convertToDbRecord()) }
            .subscribeOn(Schedulers.io())

    override fun insertRecord(record: Record): Observable<Long> =
        Observable.fromCallable { recordDao.insert(record.convertToDbRecord()) }
            .subscribeOn(Schedulers.io())

    override fun deleteRecord(record: Record): Observable<Unit> =
        Observable.fromCallable { recordDao.delete(record.convertToDbRecord()) }
            .subscribeOn(Schedulers.io())
}
