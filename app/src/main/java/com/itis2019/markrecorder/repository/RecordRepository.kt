package com.itis2019.markrecorder.repository

import com.itis2019.markrecorder.entities.Record
import io.reactivex.Observable
import io.reactivex.Single

interface RecordRepository {

    fun getAllRecords(): Observable<List<Record>>

    fun getRecordsFromFolder(folderId: Long): Observable<List<Record>>

    fun getRecord(id: Long): Single<Record>

    fun updateRecord(record: Record): Observable<Unit>

    fun insertRecord(record: Record): Observable<Long>

    fun deleteRecord(record: Record): Observable<Unit>
}
