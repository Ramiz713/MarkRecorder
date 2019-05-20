package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import com.itis2019.lecturerecorder.utils.validateEditText
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.lecture_config_fragment.*
import java.util.*

class LectureConfigFragment : BaseFragment() {

    companion object {
        const val REQUEST_CODE = 235
        const val CHOOSED_FOLDER_POSITION = "choosedFolder"
    }

    private val args: LectureConfigFragmentArgs by navArgs()
    private lateinit var folders: List<Folder>
    private var selectedFolderPosition = -1

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
        observeOnLoadFolders()
//        observeSelectedFolder()
        observeNavigateBackWithSaving()
        observeNavigateBackWithoutSaving()
        observeShowFolderChoosingDialog()
        initClickListeners()
    }

    private fun observeShowFolderChoosingDialog() =
        viewModel.showFolderChoosingDialog.observe(this, Observer {
            val items = folders.map { f -> f.name }.toTypedArray()
            val dialogFragment = FolderChoosingDialog.newInstance(items)
            dialogFragment.setTargetFragment(this, REQUEST_CODE)
            fragmentManager?.let {
                dialogFragment.show(it, "FolderChoosingDialog")
            }
        })

    private fun observeNavigateBackWithoutSaving() =
        viewModel.navigateBackWithoutSaving.observe(this, Observer {
            viewModel.deleteLectureAndMarks(args.lecture)
            findNavController(this).navigate(R.id.action_lectureConfigFragment_to_mainActivity)
        })

    private fun observeNavigateBackWithSaving() =
        viewModel.navigateBackWithSaving.observe(this, Observer {
            findNavController(this).navigate(R.id.action_lectureConfigFragment_to_mainActivity)
        })

    private fun observeOnLoadFolders() =
        viewModel.onLoadFolders().observe(this, Observer {
            folders = it
        })

    private fun initClickListeners() {
        btn_confirm.setOnClickListener {
            val lecture = args.lecture
            val lectureName = ti_lecture_name.text.toString()
            val folderName = ti_folder_name.text.toString()

            if (!validateEditText(ti_lecture_name, lectureName) || !validateEditText(ti_folder_name, folderName))
                return@setOnClickListener

            updateLectureInfo(lecture, lectureName, folderName)
        }
        btn_cancel.setOnClickListener { viewModel.cancelBtnClicked() }
        image_btn_choose_folder.setOnClickListener { viewModel.chooseFolderBtnClicked() }
    }

    private fun updateLectureInfo(lecture: Lecture, lectureName: String, folderName: String) {
        val folder = if (selectedFolderPosition != -1) folders[selectedFolderPosition]
        else Folder(
            id = 0,
            name = folderName,
            creationDate = Calendar.getInstance().time,
            background = R.drawable.gradient_yellow
        )
        val newLecture = with(lecture) {
            Lecture(
                id, lectureName, duration, creationDate, filePath, folderName,
                folder.background, folder.id
            )
        }
        viewModel.confirmBtnClicked(newLecture, folder)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            selectedFolderPosition = data?.getIntExtra(CHOOSED_FOLDER_POSITION, -1) ?: -1
            if (selectedFolderPosition == -1)
                ti_layout_folder.isEnabled = true
            else {
                ti_layout_folder.editText?.setText(folders[selectedFolderPosition].name)
                ti_layout_folder.isEnabled = false
            }
        }
    }

    //    private fun observeSelectedFolder() =
//        viewModel.fetchselectedFolder().observe(this, Observer {
//            if (it.first == getString(R.string.none_folder)) {
//
//            } else {
//                ti_layout_folder.editText?.setText(it.first)
//                ti_layout_folder.isEnabled = false
//            }
//        })
}
