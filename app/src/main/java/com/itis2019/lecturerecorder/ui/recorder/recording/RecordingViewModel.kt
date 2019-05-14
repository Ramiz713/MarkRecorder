package com.itis2019.lecturerecorder.ui.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import javax.inject.Inject

class RecordingViewModel @Inject constructor() : BaseViewModel() {

    private val isPlayingData = MutableLiveData<Boolean>()

    private val stopBtnClickedData = SingleLiveEvent<Any>()

    val navigateToLectureConfig: LiveData<Any?>
        get() = stopBtnClickedData

    fun isPlaying(): LiveData<Boolean> = isPlayingData

    fun stopBtnClicked() = stopBtnClickedData.call()

    fun playPauseBtnClicked(isPlay: Boolean) {
        isPlayingData.value = isPlay.not()
    }
}
