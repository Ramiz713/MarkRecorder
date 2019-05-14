package com.itis2019.lecturerecorder.ui.recorder.lectureConfig

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection

class LectureConfigFragment : BaseFragment() {

    override lateinit var viewModel: LectureConfigViewModel

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lecture_config_fragment, container, false)
    }

    override fun initObservers(view: View) {
    }
}
