package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.converters.convertToDbFolder
import com.itis2019.lecturerecorder.repository.converters.convertToFolder
import com.itis2019.lecturerecorder.repository.database.FolderDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FolderRepositoryImpl(private val folderDao: FolderDao) : FolderRepository {

    override fun getAllFolders(): Observable<List<Folder>> =
        folderDao.getAll()
            .map { list -> list.map { it.convertToFolder() } }
            .subscribeOn(Schedulers.io())

    override fun getFolder(id: Long): Single<Folder> =
        folderDao.getById(id)
            .map { it.convertToFolder() }
            .subscribeOn(Schedulers.io())

    override fun insertFolder(folder: Folder): Observable<Long> =
        Observable.fromCallable { folderDao.insert(folder.convertToDbFolder()) }
            .subscribeOn(Schedulers.io())

    override fun deleteFolder(folder: Folder): Observable<Unit> =
        Observable.fromCallable { folderDao.delete(folder.convertToDbFolder()) }
            .subscribeOn(Schedulers.io())
}
