package com.itis2019.markrecorder.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()
    protected val loadingData = MutableLiveData<Boolean>()
    protected var errorData = MutableLiveData<Throwable>()

    fun isLoading(): LiveData<Boolean> = loadingData

    fun error(): LiveData<Throwable> = errorData

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
