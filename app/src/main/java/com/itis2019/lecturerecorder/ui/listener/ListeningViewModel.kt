package com.itis2019.lecturerecorder.ui.listener

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import com.itis2019.lecturerecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListeningViewModel @Inject constructor(
    private val lectureRepository: RecordRepository
) : BaseViewModel() {

    fun setDataSource(currentTimeFlowable: Flowable<Int>) {
        disposables.add(
            currentTimeFlowable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { currentTimeData.value = it },
                    { errorData.value = it })
        )
    }

    private val marksData = MutableLiveData<List<Mark>>()
    private val isPlayingData = MutableLiveData<Boolean>()
    private val recordData = MutableLiveData<Record>()
    private val markItemClickedData = SingleLiveEvent<Long>()
    private val currentTimeData = MutableLiveData<Int>()

    fun isPlaying(): LiveData<Boolean> = isPlayingData

    fun getLecture(): LiveData<Record> = recordData

    fun getMarks(): LiveData<List<Mark>> = marksData

    fun getCurrentData(): LiveData<Int> = currentTimeData

    val seekWithTimecode: LiveData<Long?> = markItemClickedData

    fun markItemClicked(time: Long) {
        markItemClickedData.value = time
    }

    fun playPauseBtnClicked() {
        isPlayingData.value = isPlayingData.value?.not() ?: true
    }

    @Suppress("CheckResult")
    fun loadRecord(recordId: Long) {
        lectureRepository.getRecord(recordId)
            .doOnSubscribe { disposables.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { item ->
                    recordData.value = item
                    marksData.value = item.marks
                },
                { error -> errorData.value = error })
    }
}
