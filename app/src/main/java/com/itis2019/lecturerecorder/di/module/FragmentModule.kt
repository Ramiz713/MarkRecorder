package com.itis2019.lecturerecorder.di.module

import com.itis2019.lecturerecorder.ui.folderInfo.FolderInfoFragment
import com.itis2019.lecturerecorder.ui.folderList.FolderCreationDialog
import com.itis2019.lecturerecorder.ui.folderList.FolderListFragment
import com.itis2019.lecturerecorder.ui.folderList.FolderRenameDialog
import com.itis2019.lecturerecorder.ui.lectureList.LectureListFragment
import com.itis2019.lecturerecorder.ui.lectureList.RecordRenameDialog
import com.itis2019.lecturerecorder.ui.listener.ListeningFragment
import com.itis2019.lecturerecorder.ui.listener.ListeningMarkRenameDialog
import com.itis2019.lecturerecorder.ui.recorder.lectureConfig.FolderChoosingDialog
import com.itis2019.lecturerecorder.ui.recorder.lectureConfig.LectureConfigFragment
import com.itis2019.lecturerecorder.ui.recorder.recording.MarkRenameDialog
import com.itis2019.lecturerecorder.ui.recorder.recording.RecordingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLectureListFragment(): LectureListFragment

    @ContributesAndroidInjector
    abstract fun contributeFolderListFragment(): FolderListFragment

    @ContributesAndroidInjector
    abstract fun contributeRecordingFragment(): RecordingFragment

    @ContributesAndroidInjector
    abstract fun contributeLectureConfigFragment(): LectureConfigFragment

    @ContributesAndroidInjector
    abstract fun contributeFolderCreationDialog(): FolderCreationDialog

    @ContributesAndroidInjector
    abstract fun contributeMarkCreationDialog(): MarkRenameDialog

    @ContributesAndroidInjector
    abstract fun contributeFolderChoosingDialog(): FolderChoosingDialog

    @ContributesAndroidInjector
    abstract fun contributeFolderInfoFragment(): FolderInfoFragment

    @ContributesAndroidInjector
    abstract fun contributeListeningFragment(): ListeningFragment

    @ContributesAndroidInjector
    abstract fun contributeRecordRenameDialog(): RecordRenameDialog

    @ContributesAndroidInjector
    abstract fun contributeFolderRenameDialog(): FolderRenameDialog

    @ContributesAndroidInjector
    abstract fun contributeListeningRecordRenameDialog(): ListeningMarkRenameDialog
}
