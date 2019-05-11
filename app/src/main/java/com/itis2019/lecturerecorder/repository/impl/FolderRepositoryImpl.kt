package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.FolderRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FolderRepositoryImpl(private val folderDao: FolderDao) : FolderRepository {

    override fun getAllFolders(): Flowable<List<Folder>> =
        folderDao.getAll().observeOn(AndroidSchedulers.mainThread())

    override fun getFolder(id: Int): Single<Folder> =
        folderDao.getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertFolder(folder: Folder): Observable<Unit> =
        Observable.fromCallable { folderDao.insert(folder) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    override fun deleteFolder(folder: Folder): Observable<Unit> =
        Observable.fromCallable { folderDao.delete(folder) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
