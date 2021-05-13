package com.lib.commonsdk.mvvm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel:ViewModel() {

    private var compositeDisposable:CompositeDisposable?=null

    protected fun addDispose(disposable: Disposable){
        if(compositeDisposable==null){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable?.clear()
        super.onCleared()
    }
}