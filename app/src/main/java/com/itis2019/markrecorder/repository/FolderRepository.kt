package com.itis2019.markrecorder.repository

import com.itis2019.markrecorder.entities.Folder
import io.reactivex.Observable
import io.reactivex.Single

interface FolderRepository {

    fun getAllFolders(): Observable<List<Folder>>

    fun getFolder(id: Long): Single<Folder>

    fun updateFolder(folder: Folder): Observable<Unit>

    fun insertFolder(folder: Folder): Observable<Long>

    fun deleteFolder(folder: Folder): Observable<Unit>
}
