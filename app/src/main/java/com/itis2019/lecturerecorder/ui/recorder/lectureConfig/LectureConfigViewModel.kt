package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import javax.inject.Inject

class LectureConfigViewModel @Inject constructor() : BaseViewModel() {

    private val confirmBtnClickedData = SingleLiveEvent<Any>()
    private val cancelBtnClickedData = SingleLiveEvent<Any>()


}
