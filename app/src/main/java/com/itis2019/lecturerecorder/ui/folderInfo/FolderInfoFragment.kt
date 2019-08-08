package com.itis2019.lecturerecorder.ui.folderInfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.ui.adapters.LectureAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_folder_info.*

class FolderInfoFragment : BaseFragment() {

    private val args: FolderInfoFragmentArgs by navArgs()

    override lateinit var viewModel: FolderInfoViewModel

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_folder_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun initObservers() {
        observeLoading(progress_bar)
        observeFetchLecture()
        observeNavigateToListening()
        viewModel.fetchLectures(args.folder.id)
    }

    private fun observeFetchLecture() =
        viewModel.getLectures().observe(this, Observer {
            (rv_lectures.adapter as LectureAdapter)
                .addHeaderAndSubmitList(it, args.folder.name, getString(R.string.lectures_count, it.size))
        })

    private fun observeNavigateToListening() =
        viewModel.navigateToListening.observe(this, Observer { lecture ->
            lecture?.let {
                val action = FolderInfoFragmentDirections.actionFolderInfoFragmentToListeningFragment(it)
                findNavController(this).navigate(action)
            }
        })

    private fun initRecycler() {
        rv_lectures.layoutManager = LinearLayoutManager(activity)
        rv_lectures.adapter = LectureAdapter { lecture: Lecture -> viewModel.lectureItemClicked(lecture) }
    }

}
