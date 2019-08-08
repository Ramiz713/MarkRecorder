package com.itis2019.lecturerecorder.ui.listener

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.repository.LectureRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListeningViewModel @Inject constructor(
    private val lectureRepository: LectureRepository
) : BaseViewModel() {

    private lateinit var currentTimeFlowable: Flowable<Int>

    fun setDataSource(currentTimeFlowable: Flowable<Int>) {
        this.currentTimeFlowable = currentTimeFlowable
    }

    private val marksData = MutableLiveData<List<Mark>>()
    private val isPlayingData = MutableLiveData<Boolean>()
    private val lectureData = MutableLiveData<Lecture>()
    private val markItemClickedData = SingleLiveEvent<Long>()
    private val currentTimeData = MutableLiveData<Int>()

    fun isPlaying(): LiveData<Boolean> = isPlayingData

    val seekWithTimecode: LiveData<Long?>
        get() = markItemClickedData

    fun markItemClicked(time: Long) {
        markItemClickedData.value = time
    }

    fun playPauseBtnClicked(isPlay: Boolean) {
        isPlayingData.value = isPlay.not()
    }

    fun fetchCurrentData(): LiveData<Int> {
        disposables.add(currentTimeFlowable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currentTimeData.value = it },
                { errorData.value = it })
        )
        return  currentTimeData
    }

    fun fetchMarks(lectureId: Long): LiveData<List<Mark>> {
        disposables.add(lectureRepository.getLecture(lectureId)
            .subscribe(
                { lecture -> marksData.value = lecture.marks },
                { error -> errorData.value = error }))
        return marksData
    }
}
