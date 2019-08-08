package com.itis2019.lecturerecorder.ui.folderInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import javax.inject.Inject

class FolderInfoViewModel @Inject constructor(private val lectureRepository: LectureRepository) : BaseViewModel() {

    private val lectures = MutableLiveData<List<Lecture>>()
    private val lectureItemClicked = SingleLiveEvent<Lecture>()

    fun getLectures(): LiveData<List<Lecture>> = lectures

    val navigateToListening: LiveData<Lecture?>
        get() = lectureItemClicked

    fun lectureItemClicked(lecture: Lecture) {
        lectureItemClicked.value = lecture
    }

    fun fetchLectures(folderId: Long) {
        disposables.add(lectureRepository.getLectures(folderId)
            .doOnSubscribe { loadingData.setValue(true) }
            .doAfterNext { loadingData.setValue(false) }
            .subscribe(
                { lectures.value = it },
                { errorData.value = it }
            ))
    }
}
