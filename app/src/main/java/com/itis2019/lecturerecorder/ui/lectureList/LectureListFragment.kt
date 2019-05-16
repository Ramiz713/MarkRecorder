package com.itis2019.lecturerecorder.ui.lectureList

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.utils.dagger.FragmentInjectable
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.ui.adapters.LectureAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_lecture_list.*

class LectureListFragment : BaseFragment(), FragmentInjectable {

    override lateinit var viewModel: LectureListViewModel

    private val adapter = LectureAdapter { lecture: Lecture -> }

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

        extended_button.setOnClickListener {
            runWithPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            ) {
                viewModel.lectureRecordButtonClicked()
            }
        }
    }

    override fun initObservers(view: View) {
        observeLectureList()
        observeNavigateToRecorder()
        observeLoading(progress_bar)
        observeError(view)
    }

    private fun observeNavigateToRecorder() =
        viewModel.navigateToRecorder.observe(this, Observer {
            findNavController(this).navigate(R.id.action_navigation_lectures_to_recorderActivity)
        })

    private fun observeLectureList() =
        viewModel.onLoadLectures().observe(this, Observer {
            adapter.submitList(it)
        })

    private fun initRecycler() {
        val manager = LinearLayoutManager(activity)
        rv_lectures.adapter = adapter
        rv_lectures.layoutManager = manager
        rv_lectures.isNestedScrollingEnabled = false
        nested_scroll_view.isNestedScrollingEnabled = true

        nested_scroll_view.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY > 0) {
                extended_button.shrink(true)
                return@setOnScrollChangeListener
            }
            val firstItem = manager.findFirstCompletelyVisibleItemPosition()
            if (firstItem == 0)
                extended_button.extend(true)
        }
    }
}
