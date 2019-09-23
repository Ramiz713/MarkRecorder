package com.itis2019.markrecorder.ui.folderList

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.itis2019.markrecorder.R
import com.itis2019.markrecorder.entities.Folder
import com.itis2019.markrecorder.ui.adapters.RecordAdapter
import com.itis2019.markrecorder.ui.adapters.RecordDataItem
import com.itis2019.markrecorder.ui.base.BaseFragment
import com.itis2019.markrecorder.utils.MENU_DELETE
import com.itis2019.markrecorder.utils.MENU_RENAME
import com.itis2019.markrecorder.utils.dagger.injectViewModel
import com.itis2019.markrecorder.utils.deleteFile
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.explanation_content.*
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

        tv_emoji.text = getString(R.string.empty_folders_emoji)
        tv_explanation.text = getString(R.string.empty_folders_explanation)

        (add_folder_button).setOnClickListener { viewModel.plusButtonClicked() }
    }

    override fun initObservers() {
        observeLoading(progress_bar)
        observeFolderList()
        observeFolderCreation()
        observeFolderRenaming()
        observeRecordDelete()
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
            explanation_content.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
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

    private fun observeRecordDelete() {
        viewModel.recordDeleting.observe(this, Observer { it?.let { deleteFile(it) } })
    }

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
        val item = (rv_folders.adapter as RecordAdapter)
            .currentList[menuItem.groupId] as RecordDataItem.FolderItem
        when (menuItem.itemId) {
            MENU_RENAME -> {
                viewModel.renameMenuItemClicked(item.folder)
                true
            }
            MENU_DELETE -> {
                showAreYouSureDialog(item.folder)
                true
            }
            else -> false
        }
    }

    private fun showAreYouSureDialog(folder: Folder) {
        val alertDialog = AlertDialog.Builder(context, R.style.MarkRecorderTheme_Dialog)
        alertDialog.setTitle(getString(R.string.confirm_folder_delete))
            .setMessage(getString(R.string.folder_deleting_warning))
            .setPositiveButton(
                android.R.string.yes

            ) { dialog, _ ->
                viewModel.deleteFolder(folder)
                dialog.dismiss()
            }
        alertDialog.setNegativeButton("NO") { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }
}
