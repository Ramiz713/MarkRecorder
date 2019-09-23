package com.itis2019.markrecorder.router

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.itis2019.markrecorder.R
import com.itis2019.markrecorder.ui.folderInfo.FolderInfoFragmentDirections
import com.itis2019.markrecorder.ui.folderList.FolderListFragmentDirections
import com.itis2019.markrecorder.ui.recordList.RecordListFragmentDirections

class RouterImpl : Router {

    override fun openRecordingFragment(fragment: Fragment) =
        findNavController(fragment).navigate(R.id.action_navigation_lectures_to_recordingFragment)

    override fun openRecord(fragment: Fragment, id: Long) {
        val action = RecordListFragmentDirections.actionNavigationLecturesToListeningFragment(id)
        findNavController(fragment).navigate(action)
    }

    override fun openFolder(fragment: Fragment, id: Long) {
        val action = FolderListFragmentDirections.actionNavigationFoldersToFolderInfoFragment(id)
        findNavController(fragment).navigate(action)
    }

    override fun openRecordFromFolder(fragment: Fragment, id: Long) {
        val action = FolderInfoFragmentDirections.actionFolderInfoFragmentToListeningFragment(id)
        findNavController(fragment).navigate(action)
    }
}
