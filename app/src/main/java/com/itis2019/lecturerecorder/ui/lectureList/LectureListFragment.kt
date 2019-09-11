package com.itis2019.lecturerecorder.ui.lectureList

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.ui.adapters.RecordAdapter
import com.itis2019.lecturerecorder.ui.adapters.RecordDataItem
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

        (record_lecture_button).setOnClickListener {
            runWithPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            ) {
                viewModel.openLectureRecorder(this)
            }
        }
    }

    override fun initObservers() {
        observeLectureList()
        observeLoading(progress_bar)
    }

    private fun observeLectureList() =
        viewModel.getAllLectures().observe(this, Observer {
            (rv_records.adapter as RecordAdapter).submitList(
                listOf(RecordDataItem.Header(getString(R.string.title_recent_records))) + it
            )
        })

    private fun initRecycler() {
        val manager = LinearLayoutManager(activity)
        rv_records.adapter = RecordAdapter { id: Long -> viewModel.openLecture(this, id) }
        rv_records.layoutManager = manager
        rv_records.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (record_lecture_button).shrink()
                    return
                }
                val firstItem = manager.findFirstCompletelyVisibleItemPosition()
                if (firstItem == 0)
                    (record_lecture_button).extend()
            }
        })

        val paddingTop = rv_records.paddingTop
        rv_records.setOnApplyWindowInsetsListener { v, insets ->
            val top: Int = insets.systemWindowInsetTop + paddingTop
            v.setPadding(v.paddingStart, top, v.paddingEnd, v.paddingBottom)
            insets
        }
    }
}
