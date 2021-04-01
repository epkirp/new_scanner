package com.elkir.scanner.base

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter


abstract class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    protected val compositeDisposable = CompositeDisposable()
}