package com.itis2019.lecturerecorder.ui.folderInfo

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.ui.adapters.LectureAdapter
import com.itis2019.lecturerecorder.ui.listener.ListeningActivity
import com.itis2019.lecturerecorder.utils.dagger.DiActivity
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import kotlinx.android.synthetic.main.activity_folder_info.rv_lectures
import javax.inject.Inject

class FolderInfoActivity : DiActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FolderInfoViewModel

    private val args: FolderInfoActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_info)
        viewModel = injectViewModel(viewModelFactory)
        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        observeFetchLecture()
        observeNavigateToListening()
        viewModel.fetchLectures(args.folder.id)
    }

    private fun observeFetchLecture()=
        viewModel.getLectures().observe(this, Observer {
            (rv_lectures.adapter as LectureAdapter).submitList(it)
        })

    private fun observeNavigateToListening()=
        viewModel.navigateToListening.observe(this, Observer {
        })

    private fun initRecycler() {
        rv_lectures.layoutManager = LinearLayoutManager(this)
        rv_lectures.adapter = LectureAdapter { lecture: Lecture -> viewModel.lectureItemClicked(lecture) }
    }
}
