package com.itis2019.markrecorder.di.module

import com.itis2019.markrecorder.ui.folderInfo.FolderInfoFragment
import com.itis2019.markrecorder.ui.folderList.FolderCreationDialog
import com.itis2019.markrecorder.ui.folderList.FolderListFragment
import com.itis2019.markrecorder.ui.folderList.FolderRenameDialog
import com.itis2019.markrecorder.ui.listener.ListeningFragment
import com.itis2019.markrecorder.ui.listener.ListeningMarkRenameDialog
import com.itis2019.markrecorder.ui.recordList.RecordListFragment
import com.itis2019.markrecorder.ui.recordList.RecordRenameDialog
import com.itis2019.markrecorder.ui.recorder.lectureConfig.FolderChoosingDialog
import com.itis2019.markrecorder.ui.recorder.lectureConfig.RecordConfigFragment
import com.itis2019.markrecorder.ui.recorder.recording.MarkRenameDialog
import com.itis2019.markrecorder.ui.recorder.recording.RecordingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLectureListFragment(): RecordListFragment

    @ContributesAndroidInjector
    abstract fun contributeFolderListFragment(): FolderListFragment

    @ContributesAndroidInjector
    abstract fun contributeRecordingFragment(): RecordingFragment

    @ContributesAndroidInjector
    abstract fun contributeLectureConfigFragment(): RecordConfigFragment

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
