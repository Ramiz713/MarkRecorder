package com.itis2019.lecturerecorder.ui.folderList

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.model.Folder
import com.itis2019.lecturerecorder.ui.adapters.FolderAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_folder_list.*

class FolderListFragment : BaseFragment() {

    override lateinit var viewModel: FolderListViewModel

    private val adapter = FolderAdapter { folder: Folder -> }

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_folder_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        image_btn_create_folder.setOnClickListener { viewModel.plusButtonClicked() }
    }

    override fun initObservers(view: View) {
        observeError(view)
        observeLoading(progress_bar)
        observeFolderList()
        observeFolderCreation()
    }

    private fun observeFolderCreation() =
        viewModel.showFolderCreationDialog.observe(this, Observer {
            fragmentManager?.let {
                FolderCreationDialog().show(it, "FolderCreationFragment")
            }
        })

    private fun observeFolderList() =
        viewModel.onLoadFolders().observe(this, Observer {
            adapter.submitList(it)
        })

    private fun initRecycler() {
        val manager = if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE)
            GridLayoutManager(activity, 3)
        else GridLayoutManager(activity, 2)
        rv_folders.adapter = adapter
        rv_folders.layoutManager = manager
        rv_folders.isNestedScrollingEnabled = false
    }
}
