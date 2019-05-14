package com.itis2019.lecturerecorder.ui.folderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import javax.inject.Inject

class FolderListViewModel @Inject constructor(private val repository: FolderRepository) : BaseViewModel() {

    private var folders = MutableLiveData<List<Folder>>()
    private val folderCreationDialog = SingleLiveEvent<Any>()

    val showFolderCreationDialog: LiveData<Any?>
        get() = folderCreationDialog

    fun plusButtonClicked() = folderCreationDialog.call()

    fun onLoadFolders(): LiveData<List<Folder>> {
        disposables.add(repository.getAllFolders()
            .doOnSubscribe { loadingData.setValue(true) }
            .doAfterNext { loadingData.setValue(false) }
            .subscribe(
                { folders.value = it },
                { errorData.value = it }
            ))
        return folders
    }

    fun createFolder(folder: Folder) =
        disposables.add(repository.insertFolder(folder)
            .subscribe(
                {},
                { errorData.value = it })
        )
}
