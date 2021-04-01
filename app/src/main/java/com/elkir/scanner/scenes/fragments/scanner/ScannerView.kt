package com.elkir.scanner.scenes.fragments.scanner

import com.elkir.scanner.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface ScannerView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openShowVideoFragment(link: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeLoadingDialogVisibility(isVisible: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openErrorDialog(errorDescriptionId: Int)
}