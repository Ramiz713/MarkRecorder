package com.itis2019.lecturerecorder.repository

import com.itis2019.lecturerecorder.model.Folder
import io.reactivex.Single

interface FolderRepository {
    fun insertFolder(folder: Folder)
    fun deleteFolder(folder: Folder)
    fun getAllFolders(): Single<List<Folder>>
}
