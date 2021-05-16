package com.elkir.scanner.base

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


interface BaseView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeLoadingDialogVisibility(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openErrorDialog(titleStringId: Int)
}