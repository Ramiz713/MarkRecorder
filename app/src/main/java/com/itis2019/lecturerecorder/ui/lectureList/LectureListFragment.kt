package com.itis2019.lecturerecorder.ui.lectureList

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.ui.MainActivity
import com.itis2019.lecturerecorder.ui.adapters.LectureAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.FragmentInjectable
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_lecture_list.*

class LectureListFragment : BaseFragment(), FragmentInjectable {

    override lateinit var viewModel: LectureListViewModel

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_lecture_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        (activity as MainActivity).setOnClickListenerToRecordLectureButton {
            runWithPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            ) {
                viewModel.lectureRecordButtonClicked()
            }
        }
    }

    override fun initObservers() {
        observeLectureList()
        observeNavigateToRecorder()
        observeNavigateToListening()
        observeLoading(progress_bar)
    }

    private fun observeNavigateToRecorder() =
        viewModel.navigateToRecorder.observe(this, Observer {
            findNavController(this).navigate(R.id.action_navigation_lectures_to_recordingFragment)
        })

    private fun observeNavigateToListening() =
        viewModel.navigateToListening.observe(this, Observer { lecture ->
            lecture?.let {
                val action = LectureListFragmentDirections.actionNavigationLecturesToListeningFragment(lecture)
                findNavController(this).navigate(action)
            }
        })

    private fun observeLectureList() =
        viewModel.onLoadLectures().observe(this, Observer {
            (rv_lectures.adapter as LectureAdapter).addHeaderAndSubmitList(it, getString(R.string.recent_lectures))
        })

    private fun initRecycler() {
        val manager = LinearLayoutManager(activity)
        rv_lectures.adapter = LectureAdapter { lecture: Lecture -> viewModel.lectureItemClicked(lecture) }
        rv_lectures.layoutManager = manager

        rv_lectures.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (activity as MainActivity).shrinkRecordLectureButton()
                    return
                }
                val firstItem = manager.findFirstCompletelyVisibleItemPosition()
                if (firstItem == 0)
                    (activity as MainActivity).extendRecordLectureButton()
            }
        })
    }
}
