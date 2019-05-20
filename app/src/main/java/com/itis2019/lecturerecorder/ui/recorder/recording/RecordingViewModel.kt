package com.itis2019.lecturerecorder.ui.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.repository.MarkRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecordingViewModel @Inject constructor(
    val lectureRepository: LectureRepository,
    val markRepository: MarkRepository
) : BaseViewModel() {

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

    private val marksData = MutableLiveData<List<Mark>>()
    private val lectureId = MutableLiveData<Long>()

    val navigateToLectureConfig: LiveData<Any?>
        get() = stopBtnClickedData

    val showMarkCreationDialog: LiveData<Any?>
        get() = markBtnClickedData

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

    fun insertLecture(): LiveData<Long> {
        disposables.add(lectureRepository
            .insertLecture(Lecture())
            .subscribe(
                { lecId ->
                    lectureId.value = lecId
                    getMarks(lecId)
                },
                { errorData.value = it }))
        return lectureId
    }

    private fun getMarks(lectureId: Long) =
        disposables.add(markRepository.getLectureMarks(lectureId)
        .subscribe(
            { data -> marksData.value = data },
            { error -> errorData.value = error }))

    fun fetchMarks(): LiveData<List<Mark>> = marksData

    fun insertMark(mark: Mark) =
        disposables.add(markRepository
            .insertMark(mark)
            .subscribe(
                {}, { errorData.value = it })
        )
}
