package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

    private var folders = listOf<Folder>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        viewModel = (parentFragment as Fragment).injectViewModel(viewModelFactory)
        observeOnLoadFolders()

        val items = folders.map { f -> f.name }.toMutableList()
        items.add("None(create folder)")
        var choosedFolder: Folder
        val dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.create_folder)
            .setSingleChoiceItems(items.toTypedArray(), -1) { dialog, id ->
                Toast.makeText(activity, "$id", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()
        return dialog
    }

    private fun observeOnLoadFolders() {
        viewModel.onLoadFolders().observe(this, Observer {
            folders = it
        })
    }
}
