package com.itis2019.lecturerecorder.ui.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecordingViewModel @Inject constructor() : BaseViewModel() {

    fun setDataSource(rawBytesFlowable: Flowable<ByteArray>, chronometerFlowable: Flowable<Long>) =
        disposables.addAll(
            rawBytesFlowable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { rawBytesData.value = it },
            chronometerFlowable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { chronometerData.value = it })

    private val rawBytesData = MutableLiveData<ByteArray>()
    private val chronometerData = MutableLiveData<Long>()
    private val isPlayingData = MutableLiveData<Boolean>()
    private val stopBtnClickedData = SingleLiveEvent<Any>()
    private val markEditClickedData = SingleLiveEvent<Mark>()

    private val marksData = MutableLiveData<List<Mark>>()

    private var markId = 0

    val navigateToLectureConfig: LiveData<Any?> = stopBtnClickedData

    val showMarkCreationDialog: LiveData<Mark?> = markEditClickedData

    fun isPlaying(): LiveData<Boolean> = isPlayingData

    fun stopBtnClicked() = stopBtnClickedData.call()

    fun markEditClicked(mark: Mark) {
        markEditClickedData.value = mark
    }

    fun playPauseBtnClicked() {
        isPlayingData.value = isPlayingData.value?.not() ?: true
    }

    fun getRawBytes(): LiveData<ByteArray> = rawBytesData

    fun getChronometerData(): LiveData<Long> = chronometerData

    fun getMarks(): LiveData<List<Mark>> = marksData

    fun insertMark() = chronometerData.value?.let { timeCode ->
        Single.just(Mark(markId++, "", timeCode))
            .doOnSubscribe { disposables.add(it) }
            .map {
                val list = marksData.value?.toMutableList() ?: mutableListOf()
                list.add(it)
                list
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { marksData.value = it }
    }

    fun updateMark(mark: Mark) =
        marksData.value?.toMutableList()?.let {
            marksData.value = it.map { if (it.id == mark.id) mark else it }
        }

    fun deleteMark(mark: Mark) =
        marksData.value?.toMutableList()?.let {
            it.remove(mark)
            marksData.value = it
        }
}
