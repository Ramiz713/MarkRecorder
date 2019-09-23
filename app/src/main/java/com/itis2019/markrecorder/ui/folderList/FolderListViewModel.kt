package com.itis2019.markrecorder.ui.folderList

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.markrecorder.entities.Folder
import com.itis2019.markrecorder.repository.FolderRepository
import com.itis2019.markrecorder.repository.RecordRepository
import com.itis2019.markrecorder.router.Router
import com.itis2019.markrecorder.ui.adapters.RecordDataItem
import com.itis2019.markrecorder.ui.base.BaseViewModel
import com.itis2019.markrecorder.utils.vm.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FolderListViewModel @Inject constructor(
    private val folderRepository: FolderRepository,
    private val recordRepository: RecordRepository,
    private val router: Router
) : BaseViewModel() {

    init {
        disposables.add(
            folderRepository.getAllFolders()
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

    private val recordDeleteEvent = SingleLiveEvent<String>()
    val recordDeleting: LiveData<String?> = recordDeleteEvent

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
        disposables.add(
            folderRepository.insertFolder(folder)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe()
        )

    fun deleteFolder(folder: Folder) =
        disposables.add(folderRepository.deleteFolder(folder)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe { deleteFolderRecords(folder) }
        )

    fun updateFolder(folder: Folder) =
        disposables.add(
            folderRepository.updateFolder(folder)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe()
        )

    private fun deleteFolderRecords(folder: Folder) =
        disposables.add(recordRepository.getRecordsFromFolder(folder.id)
            .flatMap { Observable.fromIterable(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                recordDeleteEvent.value = it.filePath
                it
            }
            .flatMap { recordRepository.deleteRecord(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { errorData.value = it }
            .subscribe()
        )
}
