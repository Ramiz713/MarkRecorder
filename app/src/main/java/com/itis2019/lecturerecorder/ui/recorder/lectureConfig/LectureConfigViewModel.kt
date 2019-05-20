package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Lecture
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
    private var selectedFolder = MutableLiveData<Pair<String, Int>>()

    fun confirmBtnClicked() = confirmBtnClickedData.call()

    fun cancelBtnClicked() = cancelBtnClickedData.call()

    fun chooseFolderBtnClicked() = chooseFolderBtnClickedData.call()

    val navigateBackWithSaving: LiveData<Any?>
        get() = confirmBtnClickedData

    val navigateBackWithoutSaving: LiveData<Any?>
        get() = cancelBtnClickedData

    val showFolderChoosingDialog: LiveData<Any?>
        get() = chooseFolderBtnClickedData

    fun confirmBtnClicked(lecture: Lecture, folder: Folder) {
        if (folder.id == 0L)
            disposables.add(folderRepository.insertFolder(folder)
                .subscribe(
                    { insertLecture(lecture.copy(folderId = it)) },
                    { errorData.value = it }
                ))
        else insertLecture(lecture)
        confirmBtnClickedData.call()
    }

    private fun insertLecture(lecture: Lecture) {
        disposables.add(
            lectureRepository
                .updateLecture(lecture)
                .subscribe(
                    {},
                    { errorData.value = it })
        )
    }

    fun deleteLectureAndMarks(lecture: Lecture) {
        disposables.add(
            markRepository.deleteAllLectureBindedMarks(lecture.id)
                .subscribe(
                    { deleteLecture(lecture) },
                    { errorData.value = it })
        )
    }

    private fun deleteLecture(lecture: Lecture) =
        disposables.add(
            lectureRepository.deleteLecture(lecture)
                .subscribe(
                    {},
                    { errorData.value = it })
        )

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

    fun fetchselectedFolder(): LiveData<Pair<String, Int>> {
        return selectedFolder
    }

    fun setSelectedFolder(value: Pair<String, Int>) {
        selectedFolder.value = value
    }
}
