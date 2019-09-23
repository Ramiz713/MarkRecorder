package com.itis2019.markrecorder.ui.listener

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.markrecorder.entities.Mark
import com.itis2019.markrecorder.entities.Record
import com.itis2019.markrecorder.repository.RecordRepository
import com.itis2019.markrecorder.ui.base.BasePlayRecordViewModel
import com.itis2019.markrecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListeningViewModel @Inject constructor(
    private val lectureRepository: RecordRepository
) : BasePlayRecordViewModel() {

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

    private val recordData = MutableLiveData<Record>()
    private val markItemClickedData = SingleLiveEvent<Long>()
    private val currentTimeData = MutableLiveData<Int>()

    val seekWithTimecode: LiveData<Long?> = markItemClickedData

    fun getRecord(): LiveData<Record> = recordData

    fun getCurrentData(): LiveData<Int> = currentTimeData

    fun markItemClicked(time: Long) {
        markItemClickedData.value = time
    }

    @Suppress("CheckResult")
    fun loadRecord(recordId: Long) {
        lectureRepository.getRecord(recordId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposables.add(it) }
            .subscribe(
                { item ->
                    recordData.value = item
                    marksData.value = item.marks
                    if (item.marks.isNotEmpty())
                        markId = item.marks.last().id
                },
                { error -> errorData.value = error })
    }

    override fun insertMark() {
        getCurrentData().value?.let { timeCode ->
            Single.just(Mark(markId++, "", timeCode.toLong()))
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
    }

    override fun updateMark(mark: Mark) {
        recordData.value?.let { record ->
            marksData.value?.toMutableList()?.let { marks ->
                val updatedMarks = marks.map { if (it.id == mark.id) mark else it }
                disposables.add(lectureRepository
                    .updateRecord(record.copy(marks = updatedMarks))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { marksData.value = updatedMarks })
            }
        }
    }

    override fun deleteMark(mark: Mark) {
        recordData.value?.let { record ->
            marksData.value?.toMutableList()?.let { marks ->
                marks.remove(mark)
                disposables.add(lectureRepository
                    .updateRecord(record.copy(marks = marks))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { marksData.value = marks })
            }
        }
    }
}
