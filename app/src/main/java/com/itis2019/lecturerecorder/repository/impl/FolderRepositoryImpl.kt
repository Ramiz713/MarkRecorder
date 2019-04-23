package com.itis2019.lecturerecorder.repository.impl

import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.repository.database.FolderDao
import com.itis2019.lecturerecorder.repository.FolderRepository
import io.reactivex.Single

class FolderRepositoryImpl(folderDao: FolderDao): FolderRepository {
    override fun insertFolder(folder: Folder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteFolder(folder: Folder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllFolders(): Single<List<Folder>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
