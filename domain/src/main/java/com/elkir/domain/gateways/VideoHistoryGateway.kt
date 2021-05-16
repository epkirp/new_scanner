package com.elkir.domain.gateways

import com.elkir.domain.models.VideoItemModel
import com.elkir.domain.models.VideosModel
import com.elkir.domain.models.base.PaginationModel
import io.reactivex.Completable
import io.reactivex.Single

interface VideoHistoryGateway {

    fun getVideos(page: Int, limit: Int): Single<PaginationModel<VideosModel>>
    fun addVideo(video: VideoItemModel): Completable
}