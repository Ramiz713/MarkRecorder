package com.itis2019.lecturerecorder.ui.folderList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.ui.adapters.RecordAdapter
import com.itis2019.lecturerecorder.ui.adapters.RecordDataItem
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.MENU_DELETE
import com.itis2019.lecturerecorder.utils.MENU_RENAME
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
        (add_folder_button).setOnClickListener { viewModel.plusButtonClicked() }
        add_folder_button.hide()

    }

    override fun initObservers() {
        observeLoading(progress_bar)
        observeFolderList()
        observeFolderCreation()
        observeFolderRenaming()
    }

    private fun observeFolderCreation() =
        viewModel.showFolderCreationDialog.observe(this, Observer {
            fragmentManager?.let {
                FolderCreationDialog().show(childFragmentManager, "FolderCreationFragment")
            }
        })

    private fun observeFolderList() =
        viewModel.getFolders().observe(this, Observer {
            (rv_folders.adapter as RecordAdapter).submitList(
                listOf(RecordDataItem.Header(getString(R.string.title_your_folders))) + it
            )
            add_folder_button.show()
        })

    private fun observeFolderRenaming() =
        viewModel.showFolderRenameDialog.observe(this, Observer { folder ->
            folder?.let {
                fragmentManager?.let {
                    FolderRenameDialog.newInstance(folder)
                        .show(childFragmentManager, "RecordCreationFragment")
                }
            }
        })


    private fun initRecycler() {
        rv_folders.adapter =
            RecordAdapter({ id: Long -> viewModel.openFolder(this, id) }, menuItemClickListener)
        rv_folders.layoutManager = GridLayoutManager(activity, 2)
        (rv_folders.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when (position) {
                    0 -> 2
                    else -> 1
                }
            }

        val paddingTop = rv_folders.paddingTop
        rv_folders.setOnApplyWindowInsetsListener { v, insets ->
            val top: Int = insets.systemWindowInsetTop + paddingTop
            v.setPadding(v.paddingStart, top, v.paddingEnd, v.paddingBottom)
            insets
        }
    }

    private val menuItemClickListener = MenuItem.OnMenuItemClickListener { menuItem ->
        val item =
            (rv_folders.adapter as RecordAdapter).currentList[menuItem.groupId] as RecordDataItem.FolderItem
        when (menuItem.itemId) {
            MENU_RENAME -> {
                viewModel.renameMenuItemClicked(item.folder)
                true
            }
            MENU_DELETE -> {
                viewModel.deleteFolder(item.folder)
                true
            }
            else -> false
        }
    }
}
