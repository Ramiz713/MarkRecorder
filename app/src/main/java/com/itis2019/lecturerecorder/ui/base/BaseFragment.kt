package com.itis2019.lecturerecorder.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract val viewModel: BaseViewModel

    protected abstract fun initObservers()

    protected abstract fun initViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        observeError(view)
    }

    protected fun observeLoading(view: View) =
        viewModel.isLoading().observe(viewLifecycleOwner, Observer {
            view.visibility = if (it) View.VISIBLE else View.GONE
        })

    private fun observeError(view: View) =
        viewModel.error().observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
        })
}
