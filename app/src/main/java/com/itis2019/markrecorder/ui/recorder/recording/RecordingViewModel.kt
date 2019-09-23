package com.itis2019.markrecorder.ui.recorder.recording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.markrecorder.entities.Mark
import com.itis2019.markrecorder.router.Router
import com.itis2019.markrecorder.ui.base.BasePlayRecordViewModel
import com.itis2019.markrecorder.utils.vm.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecordingViewModel @Inject constructor(router: Router) : BasePlayRecordViewModel() {

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
    private val stopBtnClickedData = SingleLiveEvent<Any>()

    val navigateToRecordConfig: LiveData<Any?> = stopBtnClickedData

    fun stopBtnClicked() = stopBtnClickedData.call()
    fun getRawBytes(): LiveData<ByteArray> = rawBytesData

    fun getChronometerData(): LiveData<Long> = chronometerData

    override fun insertMark() {
        chronometerData.value?.let { timeCode ->
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
    }

    override fun updateMark(mark: Mark) {
        marksData.value?.toMutableList()?.let {
            marksData.value = it.map { if (it.id == mark.id) mark else it }
        }
    }

    override fun deleteMark(mark: Mark) {
        marksData.value?.toMutableList()?.let {
            it.remove(mark)
            marksData.value = it
        }
    }
}
