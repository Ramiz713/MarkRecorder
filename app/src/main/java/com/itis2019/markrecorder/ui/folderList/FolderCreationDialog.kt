package com.itis2019.markrecorder.ui.folderList

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.itis2019.markrecorder.R
import com.itis2019.markrecorder.entities.Folder
import com.itis2019.markrecorder.utils.dagger.injectViewModel
import com.itis2019.markrecorder.utils.getRandomGradientColor
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

class FolderCreationDialog : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FolderListViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        parentFragment?.let { viewModel = it.injectViewModel(viewModelFactory) }

        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_folder_creation, null)
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
                        Folder(0, name, Calendar.getInstance().time, getRandomGradientColor())
                    )
                    dismiss()
                } else textInput?.error = getString(R.string.is_not_valid)
            }
        }
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return dialog
    }
}
