package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.converters.convertToDbFolder
import com.itis2019.lecturerecorder.repository.converters.convertToFolder
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FolderRepositoryImpl(private val folderDao: FolderDao) : FolderRepository {

    override fun getAllFolders(): Flowable<List<Folder>> =
        folderDao.getAll()
            .map { list -> list.map { it.convertToFolder() } }
            .observeOn(AndroidSchedulers.mainThread())

    override fun getFolder(id: Int): Single<Folder> =
        folderDao.getById(id)
            .map { it.convertToFolder() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertFolder(folder: Folder): Observable<Unit> =
        Observable.fromCallable { folderDao.insert(folder.convertToDbFolder()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    override fun deleteFolder(folder: Folder): Observable<Unit> =
        Observable.fromCallable { folderDao.delete(folder.convertToDbFolder()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
