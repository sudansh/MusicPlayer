package com.sudansh.music.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseAndroidViewModel(app: Application) : AndroidViewModel(app) {
    private var mCompositeDisposable: CompositeDisposable? = null


    protected fun addDisposable(disposable: Disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = CompositeDisposable()
        }
        this.mCompositeDisposable?.add(disposable)
    }

    override fun onCleared() {
        if (mCompositeDisposable != null && !mCompositeDisposable?.isDisposed!!) {
            mCompositeDisposable?.clear()
        }
        super.onCleared()
    }
}
