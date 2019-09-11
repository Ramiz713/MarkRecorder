package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LectureConfigViewModel @Inject constructor(
    private val lectureRepository: RecordRepository,
    private val folderRepository: FolderRepository
) : BaseViewModel() {

    init {
        disposables.add(folderRepository
            .getAllFolders()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { folders.value = it },
                { errorData.value = it }
            )
        )
    }

    private val confirmBtnClickedData = SingleLiveEvent<Any>()
    private val cancelBtnClickedData = SingleLiveEvent<Any>()
    private val chooseFolderBtnClickedData = SingleLiveEvent<Any>()

    private val folders = MutableLiveData<List<Folder>>()
    private var selectedFolder = MutableLiveData<Folder>()

    val navigateBackWithSaving: LiveData<Any?> = confirmBtnClickedData
    val navigateBackWithoutSaving: LiveData<Any?> = cancelBtnClickedData
    val showFolderChoosingDialog: LiveData<Any?> = chooseFolderBtnClickedData

    fun cancelBtnClicked() = cancelBtnClickedData.call()

    fun chooseFolderBtnClicked() = chooseFolderBtnClickedData.call()

    fun getFolders(): LiveData<List<Folder>> = folders

    fun getSelectedFolder(): LiveData<Folder> = selectedFolder

    fun setSelectedFolder(value: Folder?) {
        selectedFolder.value = value
    }

    fun confirmBtnClicked(record: Record, folder: Folder) {
        if (folder.id == 0L)
            disposables.add(folderRepository.insertFolder(folder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { insertRecord(record.copy(folderId = it)) },
                    { errorData.value = it }
                ))
        else insertRecord(record)
    }

    private fun insertRecord(record: Record) {
        disposables.add(
            lectureRepository
                .insertRecord(record)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { confirmBtnClickedData.call() },
                    { errorData.value = it })
        )
    }
}
