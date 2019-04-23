package com.itis2019.lecturerecorder.ui.lectureList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.model.Response
import io.reactivex.rxkotlin.subscribeBy

class LectureListViewModel(private val repository: LectureRepository) : ViewModel() {

    private var loadingLiveData = MutableLiveData<Boolean>()
    private var lectures = MutableLiveData<Response<List<Lecture>>>()

    fun isLoading(): LiveData<Boolean> = loadingLiveData

    @Suppress("CheckResult")
    fun onLoadLectures(): LiveData<Response<List<Lecture>>> {
        repository.getAllLectures()
            .doOnSubscribe { loadingLiveData.setValue(true) }
            .doAfterTerminate { loadingLiveData.setValue(false) }
            .subscribeBy {
                 lectures.value = Response.success(it)
            }
        return lectures
    }
}
