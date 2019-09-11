package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.entities.Record
import io.reactivex.Observable
import io.reactivex.Single

interface RecordRepository {

    fun getAllRecords(): Observable<List<Record>>

    fun getRecordsFromFolder(folderId: Long): Observable<List<Record>>

    fun getRecord(id: Long): Single<Record>

    fun updateRecord(lecture: Record): Observable<Unit>

    fun insertRecord(lecture: Record): Observable<Long>

    fun deleteRecord(lecture: Record): Observable<Unit>
}
