package com.itis2019.lecturerecorder.ui.lectureList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import javax.inject.Inject

class LectureListViewModel @Inject constructor(private val repository: LectureRepository) : BaseViewModel() {

    private var lectures = MutableLiveData<List<Lecture>>()
    private val lectureRecordBtnClicked = SingleLiveEvent<Any>()

    val navigateToRecorder: LiveData<Any?>
        get() = lectureRecordBtnClicked

    fun lectureRecordButtonClicked() = lectureRecordBtnClicked.call()

    fun onLoadLectures(): LiveData<List<Lecture>> {
        disposables.add(repository.getAllLectures()
                .doOnSubscribe { loadingData.setValue(true) }
                .doOnNext { loadingData.setValue(false) }
                .subscribe(
                        { lectures.value = it },
                        { errorData.value = it }
                ))
        return lectures
    }
}
