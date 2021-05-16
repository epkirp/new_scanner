package com.elkir.scanner.scenes.fragments.scanner

import com.elkir.domain.models.VideoPlayerParams
import com.elkir.scanner.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface ScannerView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openVideoPlayerDialog(videoPlayerParams: VideoPlayerParams)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openErrorDialog(errorDescriptionId: Int)
}