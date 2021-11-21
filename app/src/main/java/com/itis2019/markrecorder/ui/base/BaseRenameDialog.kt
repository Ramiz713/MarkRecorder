package com.itis2019.markrecorder.ui.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.itis2019.markrecorder.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseRenameDialog<T> : DialogFragment()
    where T : Parcelable {

    companion object {
        const val EXTRA_ITEM = "item"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract val viewModel: BaseViewModel

    protected abstract val hintStringResId: Int
    protected abstract val dialogTitle: Int

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AndroidSupportInjection.inject(this)
        injectViewModel()

        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_rename, null)
        val dialog = builder.setView(view)
            .setTitle(dialogTitle)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()
        val textInput = view?.findViewById<TextInputEditText>(R.id.ti_mark_name)
        textInput?.hint = getString(hintStringResId)
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val name = textInput?.text.toString()
                val item = arguments?.getParcelable<T>(EXTRA_ITEM)

                if (name.isNotEmpty() && item != null) {
                    updateItem(item, name)
                    dismiss()
                } else textInput?.error = getString(R.string.is_not_valid)
            }
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return dialog
    }

    abstract fun injectViewModel()

    abstract fun updateItem(item: T, name: String)
}
