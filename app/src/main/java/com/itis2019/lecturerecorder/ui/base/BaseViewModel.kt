package com.itis2019.lecturerecorder.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()
    protected val loadingData = MutableLiveData<Boolean>()
    protected var errorData = MutableLiveData<Throwable>()

    fun isLoading(): MutableLiveData<Boolean> = loadingData

    fun error(): MutableLiveData<Throwable> = errorData

    override fun onCleared() = disposables.clear()
}
