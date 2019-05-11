package com.itis2019.lecturerecorder.ui.folderList

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import java.util.Calendar
import javax.inject.Inject

class FolderCreationDialog : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FolderListViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        viewModel = (parentFragment as Fragment).injectViewModel(viewModelFactory)

        val builder = AlertDialog.Builder(activity, R.style.DialogTheme)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.folder_creation_dialog, null)
        val dialog = builder.setView(view)
            .setTitle(R.string.create_folder)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val textInput = view?.findViewById<TextInputEditText>(R.id.ti_folder_name)
                val name = textInput?.text.toString()
                if (name.isNotEmpty()) {
                    viewModel.createFolder(
                        Folder(0, name, Calendar.getInstance().time, R.drawable.gradient_yellow)
                    )
                    dismiss()
                } else textInput?.error = getString(R.string.is_not_valid)
            }
        }
        return dialog
    }
}