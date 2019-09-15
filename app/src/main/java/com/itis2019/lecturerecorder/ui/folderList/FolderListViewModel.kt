package com.itis2019.lecturerecorder.ui.folderList

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.router.Router
import com.itis2019.lecturerecorder.ui.adapters.RecordDataItem
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FolderListViewModel @Inject constructor(
    private val repository: FolderRepository,
    private val router: Router
) : BaseViewModel() {

    init {
        disposables.add(repository.getAllFolders()
            .map { it.map { folder -> RecordDataItem.FolderItem(folder) } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingData.setValue(true) }
            .doAfterNext { loadingData.setValue(false) }
            .subscribe(
                { folders.value = it },
                { errorData.value = it }
            ))
    }

    private var folders = MutableLiveData<List<RecordDataItem.FolderItem>>()
    fun getFolders(): LiveData<List<RecordDataItem.FolderItem>> = folders

    private val folderCreationDialog = SingleLiveEvent<Any>()

    val showFolderCreationDialog: LiveData<Any?> = folderCreationDialog

    private val folderRenameDialogEvent = SingleLiveEvent<Folder>()
    val showFolderRenameDialog: LiveData<Folder?> = folderRenameDialogEvent

    fun renameMenuItemClicked(folder: Folder) {
        folderRenameDialogEvent.value = folder
    }

    fun plusButtonClicked() = folderCreationDialog.call()

    fun openFolder(fragment: Fragment, id: Long) = router.openFolder(fragment, id)

    fun createFolder(folder: Folder) =
        disposables.add(repository.insertFolder(folder)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe()
        )

    fun deleteFolder(folder: Folder) =
        disposables.add(repository.deleteFolder(folder)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe()
        )

    fun updateFolder(folder: Folder) =
        disposables.add(repository.updateFolder(folder)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe()
        )
}
