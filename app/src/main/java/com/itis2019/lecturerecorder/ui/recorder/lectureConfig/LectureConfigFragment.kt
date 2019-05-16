package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.lecture_config_fragment.*

class LectureConfigFragment : BaseFragment() {

    private val args: LectureConfigFragmentArgs by navArgs()
    private lateinit var folders: List<Folder>

    override lateinit var viewModel: LectureConfigViewModel

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lecture_config_fragment, container, false)
    }

    override fun initObservers(view: View) {
//        observeOnLoadFolders()
        observeNavigateBackWithSaving()
        observeNavigateBackWithoutSaving()
        observeShowFolderChoosingDialog()
        btn_confirm.setOnClickListener {
            val lecture = args.lecture
            val marks = args.marks
            val lectureName = ti_lecture_name.text.toString()
            val folderName = ti_folder_name.text.toString()

            if (!validateEditText(ti_lecture_name, lectureName) || !validateEditText(ti_folder_name, folderName))
                return@setOnClickListener
            val newLecture = Lecture(0, lectureName, lecture.duration, lecture.creationDate,
                lecture.filePath, folderName, R.drawable.gradient_orange, 1)
            viewModel.confirmBtnClicked(newLecture, marks.asList())
        }
        btn_cancel.setOnClickListener { viewModel.cancelBtnClicked() }
        image_btn_choose_folder.setOnClickListener { viewModel.chooseFolderBtnClicked() }
    }

    private fun observeShowFolderChoosingDialog() =
        viewModel.showFolderChoosingDialog.observe(this, Observer {
            fragmentManager?.let {
                FolderChoosingDialog().show(it, "FolderChoosingDialog")
            }
        })

    private fun observeNavigateBackWithoutSaving() =
        viewModel.navigateBackWithoutSaving.observe(this, Observer {

        })

    private fun observeNavigateBackWithSaving() =
        viewModel.navigateBackWithSaving.observe(this, Observer {
            findNavController(this).navigate(R.id.action_lectureConfigFragment_to_mainActivity)
        })

//    private fun observeOnLoadFolders() =
//        viewModel.onLoadFolders().observe(this, Observer { folders = it })

    private fun validateEditText(editText: EditText, text: String): Boolean {
        if (TextUtils.isEmpty(text)) {
            editText.error = "Cannot be empty!"
            return false
        }
        return true
    }
}
