package com.itis2019.markrecorder.ui.recordList

import androidx.fragment.app.Fragment
import com.itis2019.markrecorder.repository.RecordRepository
import com.itis2019.markrecorder.router.Router
import com.itis2019.markrecorder.ui.adapters.RecordDataItem
import com.itis2019.markrecorder.ui.base.BaseRecordListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class RecordListViewModel @Inject constructor(
    repository: RecordRepository,
    private val router: Router
) : BaseRecordListViewModel(repository) {

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

    fun openLectureRecorder(fragment: Fragment) = router.openRecordingFragment(fragment)

    fun openLecture(fragment: Fragment, id: Long) = router.openRecord(fragment, id)
}
