package com.itis2019.markrecorder.ui.listener

import android.os.Bundle
import com.itis2019.markrecorder.R
import com.itis2019.markrecorder.entities.Mark
import com.itis2019.markrecorder.ui.base.BaseRenameDialog
import com.itis2019.markrecorder.utils.dagger.injectViewModel

class ListeningMarkRenameDialog : BaseRenameDialog<Mark>() {

    companion object {

        fun newInstance(mark: Mark): ListeningMarkRenameDialog {
            val dialog = ListeningMarkRenameDialog()
            dialog.arguments = Bundle().apply { putParcelable(EXTRA_ITEM, mark) }
            return dialog
        }
    }

    override lateinit var viewModel: ListeningViewModel

    override val hintStringResId = R.string.hint_mark_name

    override val dialogTitle = R.string.rename_mark

    override fun injectViewModel() {
        parentFragment?.let { viewModel = it.injectViewModel(viewModelFactory) }
    }

    override fun updateItem(item: Mark, name: String) {
        viewModel.updateMark(item.copy(name = name))
    }
}
