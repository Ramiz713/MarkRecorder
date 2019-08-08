package com.itis2019.lecturerecorder.ui.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecordingViewModel @Inject constructor() : BaseViewModel() {

    private lateinit var rawBytesFlowable: Flowable<ByteArray>
    private lateinit var chronometerFlowable: Flowable<Long>

    fun setDataSource(rawBytesFlowable: Flowable<ByteArray>, chronometerFlowable: Flowable<Long>) {
        this.rawBytesFlowable = rawBytesFlowable
        this.chronometerFlowable = chronometerFlowable
    }

    private val rawBytesData = MutableLiveData<ByteArray>()
    private val chronometerData = MutableLiveData<Long>()
    private val isPlayingData = MutableLiveData<Boolean>()
    private val stopBtnClickedData = SingleLiveEvent<Any>()
    private val markBtnClickedData = SingleLiveEvent<Any>()

    private val marksList: MutableList<Mark> = mutableListOf()
    private val marksData = MutableLiveData<List<Mark>>()

    val navigateToLectureConfig: LiveData<Any?>
        get() = stopBtnClickedData

    val showMarkCreationDialog: LiveData<Any?>
        get() = markBtnClickedData

    val getMarks: List<Mark>
        get() = marksList

    fun isPlaying(): LiveData<Boolean> = isPlayingData

    fun stopBtnClicked() = stopBtnClickedData.call()

    fun markBtnClicked() = markBtnClickedData.call()

    fun playPauseBtnClicked(isPlay: Boolean) {
        isPlayingData.value = isPlay.not()
    }

    fun fetchRawBytes(): LiveData<ByteArray> {
        disposables.add(rawBytesFlowable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { rawBytesData.value = it })
        return rawBytesData
    }

    fun fetchChronometerData(): LiveData<Long> {
        disposables.add(chronometerFlowable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { chronometerData.value = it })
        return chronometerData
    }

    fun fetchMarks(): LiveData<List<Mark>> = marksData

    fun insertMark(mark: Mark) {
        disposables.add(Single.just(Mark(marksList.size, mark.name, mark.time))
            .map {
                marksList.add(it)
                marksList.toList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> marksData.value = it })
    }

}
