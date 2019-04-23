package com.itis2019.lecturerecorder.di.module

import androidx.lifecycle.ViewModelProvider
import com.itis2019.lecturerecorder.ui.lectureList.LectureListViewModel
import com.itis2019.lecturerecorder.utils.ViewModelFactory
import com.itis2019.lecturerecorder.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun viewModelModule() = Kodein.Module("viewModel") {

    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(dkodein) }

    bindViewModel<LectureListViewModel>() with singleton { LectureListViewModel(instance()) }
}
