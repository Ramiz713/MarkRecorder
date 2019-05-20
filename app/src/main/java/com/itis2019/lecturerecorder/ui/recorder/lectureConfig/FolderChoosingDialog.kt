package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.ui.recorder.lectureConfig.LectureConfigFragment.Companion.CHOOSED_FOLDER_POSITION
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FolderChoosingDialog : DialogFragment() {

    companion object {
        private const val EXTRA_FOLDERS = "time"

        fun newInstance(folders: Array<String>): FolderChoosingDialog {
            val dialog = FolderChoosingDialog()
            val args = Bundle().apply {
                putStringArray(EXTRA_FOLDERS, folders)
            }
            dialog.arguments = args
            return dialog
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LectureConfigViewModel

    private var folders = listOf<Folder>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        viewModel = (parentFragment as Fragment).injectViewModel(viewModelFactory)

        val items = arguments?.getStringArray(EXTRA_FOLDERS)?.toMutableList() ?: ArrayList<String>()
        items.add(getString(R.string.none_folder))
        val dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.create_folder)
            .setSingleChoiceItems(items.toTypedArray(), -1) { dialog, position ->
                //                viewModel.setSelectedFolder(Pair(items[id], id))
                val intent = Intent()
                val currentPosition = if (position + 1 == items.size) -1 else position
                intent.putExtra(CHOOSED_FOLDER_POSITION, currentPosition)
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                dialog.dismiss()
            }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            }
        }
        return dialog
    }
}
