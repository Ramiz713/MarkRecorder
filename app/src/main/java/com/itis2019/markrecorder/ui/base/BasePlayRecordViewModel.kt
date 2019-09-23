package com.itis2019.markrecorder.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.markrecorder.entities.Mark
import com.itis2019.markrecorder.utils.vm.SingleLiveEvent

abstract class BasePlayRecordViewModel : BaseViewModel() {

    private val isPlayingData = MutableLiveData<Boolean>()
    private val markEditClickedData = SingleLiveEvent<Mark>()

    protected val marksData = MutableLiveData<List<Mark>>()
    protected var markId = 0

    val showMarkRenameDialog: LiveData<Mark?> = markEditClickedData

    fun getMarks(): LiveData<List<Mark>> = marksData

    fun isPlaying(): LiveData<Boolean> = isPlayingData

    fun markEditClicked(mark: Mark) {
        markEditClickedData.value = mark
    }

    fun playPauseBtnClicked() {
        isPlayingData.value = isPlayingData.value?.not() ?: true
    }

    abstract fun insertMark()

    abstract fun updateMark(mark: Mark)

    abstract fun deleteMark(mark: Mark)
}
