package com.itis2019.lecturerecorder.ui.lectureList

import androidx.fragment.app.Fragment
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.router.Router
import com.itis2019.lecturerecorder.ui.adapters.RecordDataItem
import com.itis2019.lecturerecorder.ui.base.BaseLectureListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LectureListViewModel @Inject constructor(
    repository: RecordRepository,
    private val router: Router
) : BaseLectureListViewModel(repository) {

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
