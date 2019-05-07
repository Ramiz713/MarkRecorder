package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.utils.subscribeObservableOnIoObserveOnUi
import com.itis2019.lecturerecorder.utils.subscribeSingleOnIoObserveOnUi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class FolderRepositoryImpl(private val folderDao: FolderDao) : FolderRepository {

    override fun getAllFolders(): Flowable<List<Folder>> =
            folderDao.getAll().observeOn(AndroidSchedulers.mainThread())

    override fun getFolder(id: Int): Single<Folder> =
            folderDao.getById(id).subscribeSingleOnIoObserveOnUi()

    override fun insertFolder(folder: Folder) {
        Observable.fromCallable { folderDao.insert(folder) }
                .subscribeObservableOnIoObserveOnUi()
                .subscribe()
    }

    override fun deleteFolder(folder: Folder) {
        Observable.fromCallable { folderDao.delete(folder) }
                .subscribeObservableOnIoObserveOnUi()
                .subscribe()
    }
}
