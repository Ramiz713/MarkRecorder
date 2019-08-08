package com.itis2019.lecturerecorder.ui.recorder.recording

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MarkCreationDialog : DialogFragment() {

    companion object {
        private const val EXTRA_TIME = "time"

        fun newInstance(time: Long): MarkCreationDialog {
            val dialog = MarkCreationDialog()
            val args = Bundle().apply {
                putLong(EXTRA_TIME, time)
            }
            dialog.arguments = args
            return dialog
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RecordingViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        val fragment = parentFragment?.childFragmentManager?.fragments?.find { it is RecordingFragment }
            ?: Fragment()
        viewModel = (fragment).injectViewModel(viewModelFactory)

        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.mark_creation_dialog, null)
        val dialog = builder.setView(view)
            .setTitle(R.string.create_mark)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val textInput = view?.findViewById<TextInputEditText>(R.id.ti_mark_name)
                val name = textInput?.text.toString()
                val time = arguments?.getLong(EXTRA_TIME)?: 0

                if (name.isNotEmpty()) {
                    viewModel.insertMark(Mark(0, name, time))
                    dismiss()
                } else textInput?.error = getString(R.string.is_not_valid)
            }
        }
        return dialog
    }
}
