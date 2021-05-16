package com.elkir.scanner.scenes.fragments.video_history

import com.elkir.domain.models.VideoPlayerParams
import com.elkir.domain.models.VideosModel
import com.elkir.scanner.base.pagination.BasePaginationView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface VideoHistoryView : BasePaginationView<VideosModel> {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openVideoPlayerDialog(videoPlayerParams: VideoPlayerParams)
}