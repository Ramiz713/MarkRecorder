package com.itis2019.lecturerecorder.router

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.ui.folderInfo.FolderInfoFragmentDirections
import com.itis2019.lecturerecorder.ui.folderList.FolderListFragmentDirections
import com.itis2019.lecturerecorder.ui.lectureList.LectureListFragmentDirections

class RouterImpl : Router {

    override fun openRecordingFragment(fragment: Fragment) =
        findNavController(fragment).navigate(R.id.action_navigation_lectures_to_recordingFragment)

    override fun openRecord(fragment: Fragment, id: Long) {
        val action = LectureListFragmentDirections.actionNavigationLecturesToListeningFragment(id)
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

    override fun openLectureConfiguration(fragment: Fragment, lecture: Record) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
