package com.itis2019.markrecorder.ui.recorder.lectureConfig

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itis2019.markrecorder.R
import com.itis2019.markrecorder.entities.Folder
import com.itis2019.markrecorder.entities.Record
import com.itis2019.markrecorder.ui.base.BaseFragment
import com.itis2019.markrecorder.utils.dagger.injectViewModel
import com.itis2019.markrecorder.utils.deleteFile
import com.itis2019.markrecorder.utils.getRandomGradientColor
import com.itis2019.markrecorder.utils.validateEditText
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_lecture_config.*
import java.util.*

class RecordConfigFragment : BaseFragment() {

    private val args: RecordConfigFragmentArgs by navArgs()

    override lateinit var viewModel: RecordConfigViewModel

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(activity, "back pressed", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lecture_config, container, false)
    }

    override fun initObservers() {
        observeSelectedFolder()
        observeNavigateBackWithSaving()
        observeNavigateBackWithoutSaving()
        observeShowFolderChoosingDialog()
        initClickListeners()
    }

    private fun observeShowFolderChoosingDialog() =
        viewModel.showFolderChoosingDialog.observe(this, Observer {
            val dialogFragment = FolderChoosingDialog()
            fragmentManager?.let {
                dialogFragment.show(childFragmentManager, "FolderChoosingDialog")
            }
        })

    private fun observeNavigateBackWithoutSaving() =
        viewModel.navigateBackWithoutSaving.observe(this, Observer {
            deleteFile(args.record.filePath)
            findNavController(this).navigate(R.id.action_lectureConfigFragment_to_navigation_lectures)
        })

    private fun observeNavigateBackWithSaving() =
        viewModel.navigateBackWithSaving.observe(this, Observer {
            findNavController(this).navigate(R.id.action_lectureConfigFragment_to_navigation_lectures)
        })

    private fun observeSelectedFolder() {
        viewModel.getSelectedFolder().observe(this, Observer {
            it?.let {
                ti_layout_folder.editText?.setText(it.name)
                ti_layout_folder.isEnabled = false
                return@Observer
            }
            ti_layout_folder.isEnabled = true
            ti_layout_folder.editText?.text?.clear()
        })
    }

    private fun initClickListeners() {
        btn_confirm.setOnClickListener {
            val lecture = args.record
            val lectureName = ti_lecture_name.text.toString()
            val folderName = ti_folder_name.text.toString()

            if (!validateEditText(ti_lecture_name, lectureName) || !validateEditText(
                    ti_folder_name,
                    folderName
                )
            )
                return@setOnClickListener

            updateLectureInfo(lecture, lectureName, folderName)
        }
        btn_cancel.setOnClickListener { viewModel.cancelBtnClicked() }
        image_btn_choose_folder.setOnClickListener { viewModel.chooseFolderBtnClicked() }
    }

    private fun updateLectureInfo(record: Record, recordName: String, folderName: String) {
        val folder = viewModel.getSelectedFolder().value ?: Folder(
            id = 0,
            name = folderName,
            creationDate = Calendar.getInstance().time,
            background = getRandomGradientColor()
        )
        val newLecture = with(record) {
            Record(
                id, recordName, marks, duration, creationDate, filePath, folderName,
                folder.background, folder.id
            )
        }
        viewModel.confirmBtnClicked(newLecture, folder)
    }
}
