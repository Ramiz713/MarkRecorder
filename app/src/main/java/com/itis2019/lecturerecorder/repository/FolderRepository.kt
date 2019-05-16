package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.entities.Folder
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface FolderRepository {

    fun getAllFolders(): Flowable<List<Folder>>

    fun getFolder(id: Int): Single<Folder>

    fun insertFolder(folder: Folder): Observable<Unit>

    fun deleteFolder(folder: Folder): Observable<Unit>
}
