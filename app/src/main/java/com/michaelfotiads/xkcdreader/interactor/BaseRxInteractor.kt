package com.michaelfotiads.xkcdreader.interactor

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseRxInteractor {

    private val subscriptions = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        subscriptions.add(disposable)
    }

    fun clear() {
        subscriptions.clear()
    }
}
