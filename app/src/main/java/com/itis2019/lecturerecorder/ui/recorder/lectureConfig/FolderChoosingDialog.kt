package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FolderChoosingDialog : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LectureConfigViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        parentFragment?.let { viewModel = it.injectViewModel(viewModelFactory) }

        val folders = viewModel.getFolders().value ?: listOf()
        val foldersName = folders.map { it.name }.toMutableList()
        var selectedFolder: Folder? = null
        foldersName.add(getString(R.string.none_folder))
        val dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.choose_folder)
            .setPositiveButton(R.string.ok, null)
            .setSingleChoiceItems(foldersName.toTypedArray(), -1) { _, position ->
                if (position + 1 != foldersName.size) selectedFolder = folders[position]
            }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                viewModel.setSelectedFolder(selectedFolder)
                dismiss()
            }
        }
        return dialog
    }
}
