package com.itis2019.markrecorder.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis2019.markrecorder.ui.base.BaseRecordListViewModel
import com.itis2019.markrecorder.ui.folderInfo.FolderInfoViewModel
import com.itis2019.markrecorder.ui.folderList.FolderListViewModel
import com.itis2019.markrecorder.ui.listener.ListeningViewModel
import com.itis2019.markrecorder.ui.recordList.RecordListViewModel
import com.itis2019.markrecorder.ui.recorder.lectureConfig.RecordConfigViewModel
import com.itis2019.markrecorder.ui.recorder.recording.RecordingViewModel
import com.itis2019.markrecorder.utils.dagger.ViewModelKey
import com.itis2019.markrecorder.utils.vm.ViewModelFactory
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
    @ViewModelKey(RecordListViewModel::class)
    internal abstract fun recordListViewModel(viewModel: RecordListViewModel): ViewModel

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
    @ViewModelKey(RecordConfigViewModel::class)
    internal abstract fun recordConfigViewModel(viewModel: RecordConfigViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListeningViewModel::class)
    internal abstract fun recordListeningViewModel(viewModel: ListeningViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FolderInfoViewModel::class)
    internal abstract fun recordFolderInfoViewModel(viewModel: FolderInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BaseRecordListViewModel::class)
    internal abstract fun baseRecordListViewModel(viewModel: BaseRecordListViewModel): ViewModel
}
