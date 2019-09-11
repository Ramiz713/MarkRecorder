package com.itis2019.lecturerecorder.ui.folderInfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.ui.adapters.RecordAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_folder_info.*

class FolderInfoFragment : BaseFragment() {

    private val args: FolderInfoFragmentArgs by navArgs()

    override lateinit var viewModel: FolderInfoViewModel

    private var visibilitySetting = 0
    private var previousStatusBarColor = 0

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_folder_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getFolder(args.folderId)
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.apply {
            previousStatusBarColor = statusBarColor
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.parseColor("#33000000")
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = previousStatusBarColor
        }
    }

    override fun initObservers() {
        observeLoading(progress_bar)
        observeFolder()
        observeLectures()
    }

    private fun observeFolder() {
        viewModel.getFolder().observe(this, Observer {
            records_container.background = resources.getDrawable(it.background)
        })
    }

    private fun observeLectures() =
        viewModel.getLectures().observe(this, Observer {
            (rv_records.adapter as RecordAdapter).submitList(it)
        })

    private fun initRecycler() {
        rv_records.layoutManager = LinearLayoutManager(activity)
        rv_records.adapter = RecordAdapter { id: Long -> viewModel.openRecordFromFolder(this, id) }

        val paddingTop = rv_records.paddingTop
        rv_records.setOnApplyWindowInsetsListener { v, insets ->
            val top: Int = insets.systemWindowInsetTop + paddingTop
            v.setPadding(v.paddingStart, top, v.paddingEnd, v.paddingBottom)
            insets
        }
    }
}
