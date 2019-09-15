package com.itis2019.lecturerecorder.ui.folderInfo

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.repository.FolderRepository
import com.itis2019.lecturerecorder.repository.RecordRepository
import com.itis2019.lecturerecorder.router.Router
import com.itis2019.lecturerecorder.ui.adapters.RecordDataItem
import com.itis2019.lecturerecorder.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FolderInfoViewModel @Inject constructor(
    private val lectureRepository: RecordRepository,
    private val folderRepository: FolderRepository,
    private val router: Router
) : BaseViewModel() {

    private val lectures = MutableLiveData<List<RecordDataItem>>()
    private val folder = MutableLiveData<Folder>()
    private lateinit var header: RecordDataItem.Header

    fun getLectures(): LiveData<List<RecordDataItem>> = lectures

    fun getFolder(): LiveData<Folder> = folder

    fun openRecordFromFolder(fragment: Fragment, id: Long) =
        router.openRecordFromFolder(fragment, id)

    fun getFolder(folderId: Long) {
        disposables.add(folderRepository.
            getFolder(folderId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingData.setValue(true) }
                .doAfterTerminate { loadingData.setValue(false) }
                .subscribe(
                    {
                        folder.value = it
                        header =
                            RecordDataItem.Header(getFolder().value?.name ?: "", isWhite = true)
                        lectures.value = listOf(header)
                        getLectures(folderId)
                    },
                    { errorData.value = it }
                ))
    }

    private fun getLectures(folderId: Long) {
        disposables.add(lectureRepository.getRecordsFromFolder(folderId)
            .map { it.map { record -> RecordDataItem.RecordItemFolderVersion(record) } }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingData.setValue(true) }
            .doAfterNext { loadingData.setValue(false) }
            .subscribe(
                { lectures.value = listOf(header.copy(recordsCount = it.count())) + it },
                { errorData.value = it }
            ))
    }
}
