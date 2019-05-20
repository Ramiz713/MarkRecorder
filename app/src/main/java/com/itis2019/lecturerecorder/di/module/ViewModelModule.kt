package com.itis2019.lecturerecorder.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis2019.lecturerecorder.ui.folderInfo.FolderInfoViewModel
import com.itis2019.lecturerecorder.ui.folderList.FolderListViewModel
import com.itis2019.lecturerecorder.ui.lectureList.LectureListViewModel
import com.itis2019.lecturerecorder.ui.listener.ListeningViewModel
import com.itis2019.lecturerecorder.ui.recorder.lectureConfig.LectureConfigViewModel
import com.itis2019.lecturerecorder.ui.recorder.recording.RecordingViewModel
import com.itis2019.lecturerecorder.utils.dagger.ViewModelKey
import com.itis2019.lecturerecorder.utils.vm.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LectureListViewModel::class)
    internal abstract fun lectureListViewModel(viewModel: LectureListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FolderListViewModel::class)
    internal abstract fun folderListViewModel(viewModel: FolderListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordingViewModel::class)
    internal abstract fun recordingViewModel(viewModel: RecordingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LectureConfigViewModel::class)
    internal abstract fun lectureConfigViewModel(viewModel: LectureConfigViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListeningViewModel::class)
    internal abstract fun lectureListeningViewModel(viewModel: ListeningViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FolderInfoViewModel::class)
    internal abstract fun lectureFolderInfoViewModel(viewModel: FolderInfoViewModel): ViewModel
}
