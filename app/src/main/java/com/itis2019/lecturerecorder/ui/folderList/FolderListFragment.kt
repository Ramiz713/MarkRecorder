package com.itis2019.lecturerecorder.ui.folderList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.ui.adapters.FolderAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_folder_list.*

class FolderListFragment : BaseFragment() {

    override lateinit var viewModel: FolderListViewModel

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
        observeNavigateFolderInfo()
    }

    private fun observeFolderCreation() =
        viewModel.showFolderCreationDialog.observe(this, Observer {
            fragmentManager?.let {
                FolderCreationDialog().show(it, "FolderCreationFragment")
            }
        })

    private fun observeFolderList() =
        viewModel.onLoadFolders().observe(this, Observer {
            (rv_folders.adapter as FolderAdapter).submitList(it)
        })

    private fun observeNavigateFolderInfo() =
        viewModel.navigateToFolderInfo.observe(this, Observer {folder ->
           folder?.let {
               val action = FolderListFragmentDirections.actionNavigationFoldersToFolderInfoActivity(it)
               findNavController(this).navigate(action)
           }
        })

    private fun initRecycler() {
        rv_folders.adapter = FolderAdapter { folder: Folder -> viewModel.folderItemClicked(folder) }
        rv_folders.layoutManager = GridLayoutManager(activity, 2)
        rv_folders.isNestedScrollingEnabled = false
    }
}
