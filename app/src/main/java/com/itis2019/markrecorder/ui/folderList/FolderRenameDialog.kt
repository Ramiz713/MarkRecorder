package com.itis2019.markrecorder.ui.folderList

import android.os.Bundle
import com.itis2019.markrecorder.R
import com.itis2019.markrecorder.entities.Folder
import com.itis2019.markrecorder.ui.base.BaseRenameDialog
import com.itis2019.markrecorder.utils.dagger.injectViewModel

class FolderRenameDialog : BaseRenameDialog<Folder>() {

    companion object {

        fun newInstance(folder: Folder): FolderRenameDialog {
            val dialog = FolderRenameDialog()
            dialog.arguments = Bundle().apply { putParcelable(EXTRA_ITEM, folder) }
            return dialog
        }
    }

    override lateinit var viewModel: FolderListViewModel

    override val hintStringResId = R.string.hint_folder_rename

    override val dialogTitle = R.string.rename_folder

    override fun injectViewModel() {
        parentFragment?.let { viewModel = it.injectViewModel(viewModelFactory) }
    }

    override fun updateItem(item: Folder, name: String) {
        viewModel.updateFolder(item.copy(name = name))
    }
}
