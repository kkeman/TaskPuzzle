package com.service.codingtest.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    var cDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        cDisposable.dispose()
    }
}