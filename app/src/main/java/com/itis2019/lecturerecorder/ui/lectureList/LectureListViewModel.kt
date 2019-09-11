package com.itis2019.lecturerecorder.ui.lectureList

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.router.Router
import com.itis2019.lecturerecorder.ui.adapters.RecordDataItem
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LectureListViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val router: Router
) : BaseViewModel() {

    init {
        disposables.add(repository.getAllRecords()
            .map { it.map { record -> RecordDataItem.RecordItem(record) } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingData.setValue(true) }
            .doOnNext { loadingData.setValue(false) }
            .subscribe(
                { records.value = it },
                { errorData.value = it }
            ))
    }

    private var records = MutableLiveData<List<RecordDataItem.RecordItem>>()

    fun openLectureRecorder(fragment: Fragment) = router.openRecordingFragment(fragment)

    fun openLecture(fragment: Fragment, id: Long) = router.openRecord(fragment, id)

    fun getAllLectures(): LiveData<List<RecordDataItem.RecordItem>> = records
}
