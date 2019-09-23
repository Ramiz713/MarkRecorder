package com.itis2019.lecturerecorder.ui.lectureList

import android.os.Bundle
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.ui.base.BaseLectureListViewModel
import com.itis2019.lecturerecorder.ui.base.BaseRenameDialog
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel

class RecordRenameDialog : BaseRenameDialog<Record>() {

    companion object {

        fun newInstance(record: Record): RecordRenameDialog {
            val dialog = RecordRenameDialog()
            dialog.arguments = Bundle().apply { putParcelable(EXTRA_ITEM, record) }
            return dialog
        }
    }

    override lateinit var viewModel: BaseLectureListViewModel

    override val hintStringResId = R.string.hint_record_rename

    override val dialogTitle = R.string.rename_record

    override fun injectViewModel() {
        parentFragment?.let { viewModel = it.injectViewModel(viewModelFactory) }
    }

    override fun updateItem(item: Record, name: String) {
        viewModel.updateRecord(item.copy(name = name))
    }
}
