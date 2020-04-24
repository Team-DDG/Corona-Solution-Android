package com.golddog.mask_location.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val composite: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun addDisposable(disposable: Disposable) {
        composite.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}