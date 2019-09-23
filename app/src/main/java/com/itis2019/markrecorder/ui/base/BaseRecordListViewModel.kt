package com.itis2019.markrecorder.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis2019.markrecorder.entities.Record
import com.itis2019.markrecorder.repository.RecordRepository
import com.itis2019.markrecorder.ui.adapters.RecordDataItem
import com.itis2019.markrecorder.utils.vm.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

open class BaseRecordListViewModel @Inject constructor(
    private val repository: RecordRepository
) : BaseViewModel() {

    protected var records = MutableLiveData<List<RecordDataItem>>()

    private val recordDeleteEvent = SingleLiveEvent<String>()
    val recordDeleting: LiveData<String?> = recordDeleteEvent

    private val recordRenameDialogEvent = SingleLiveEvent<Record>()
    val showRecordNameEditDialog: LiveData<Record?> = recordRenameDialogEvent

    fun renameMenuItemClicked(record: Record) {
        recordRenameDialogEvent.value = record
    }

    open fun getRecords(): LiveData<List<RecordDataItem>> = records

    fun deleteRecord(record: Record) =
        disposables.add(
            repository
                .deleteRecord(record)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { recordDeleteEvent.value = record.filePath },
                    { errorData.value = it })
        )

    fun updateRecord(record: Record) =
        disposables.add(
            repository
                .updateRecord(record)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { errorData.value = it }
                .subscribe()
        )
}
