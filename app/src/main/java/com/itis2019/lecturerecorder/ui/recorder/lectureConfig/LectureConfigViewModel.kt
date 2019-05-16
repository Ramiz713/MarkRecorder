package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.repository.MarkRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import javax.inject.Inject

class LectureConfigViewModel @Inject constructor(
    private val lectureRepository: LectureRepository,
    private val markRepository: MarkRepository,
    private val folderRepository: FolderRepository
) : BaseViewModel() {

    private val confirmBtnClickedData = SingleLiveEvent<Any>()
    private val cancelBtnClickedData = SingleLiveEvent<Any>()
    private val chooseFolderBtnClickedData = SingleLiveEvent<Any>()
    private val folders = MutableLiveData<List<Folder>>()

    fun confirmBtnClicked() = confirmBtnClickedData.call()

    fun cancelBtnClicked() = cancelBtnClickedData.call()

    fun chooseFolderBtnClicked() = chooseFolderBtnClickedData.call()

    val navigateBackWithSaving: LiveData<Any?>
        get() = confirmBtnClickedData

    val navigateBackWithoutSaving: LiveData<Any?>
        get() = cancelBtnClickedData

    val showFolderChoosingDialog: LiveData<Any?>
        get() = chooseFolderBtnClickedData

    fun confirmBtnClicked(lecture: Lecture, marks: List<Mark>) {
        disposables.add(lectureRepository.insertLecture(lecture).subscribe(
            {},
            { errorData.value = it })
        )
        confirmBtnClickedData.call()
    }

    fun onLoadFolders(): LiveData<List<Folder>> {
        disposables.add(folderRepository
            .getAllFolders()
            .subscribe(
                { folders.value = it },
                { errorData.value = it }
            )
        )
        return folders
    }
}
