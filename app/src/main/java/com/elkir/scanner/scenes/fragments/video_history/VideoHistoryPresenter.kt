package com.elkir.scanner.scenes.fragments.video_history

import com.elkir.domain.gateways.PublicFileGateway
import com.elkir.domain.gateways.VideoHistoryGateway
import com.elkir.domain.models.VideoItemModel
import com.elkir.domain.models.VideoPlayerParams
import com.elkir.domain.models.VideosModel
import com.elkir.domain.models.base.PaginationModel
import com.elkir.scanner.base.pagination.BasePaginationPresenter
import com.elkir.scanner.extensions.handleError
import com.elkir.scanner.utils.getCurrentDateAsString
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class VideoHistoryPresenter @Inject constructor(
    private val publicFileGateway: PublicFileGateway,
    private val videoHistoryGateway: VideoHistoryGateway
) : BasePaginationPresenter<VideosModel, VideoHistoryView>() {

    override fun getItems(page: Int, limit: Int): Single<PaginationModel<VideosModel>> {
        return videoHistoryGateway.getVideos(page, limit)
    }

    fun onVideoClicked(videoItemModel: VideoItemModel) {
        if (!videoItemModel.isLocalVideo) {
            getRemoteVideo(videoItemModel.publicKey)
        } else {
            viewState.openVideoPlayerDialog(
                VideoPlayerParams(
                    videoUri = videoItemModel.path,
                    isLocalVideo = false
                )
            )
        }
    }

    private fun getRemoteVideo(publicKey: String) {
        publicFileGateway.getPublicResources(publicKey)
            .map {
                VideoItemModel(
                    path = it.path,
                    publicKey = publicKey,
                    name = it.name,
                    duration = 0,
                    isLocalVideo = false,
                    lastWatchDate = getCurrentDateAsString()
                )
            }
            .flatMap {
                videoHistoryGateway.addVideo(it)
                    .andThen(Single.just(it))
            }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.changeLoadingDialogVisibility(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { viewState.changeLoadingDialogVisibility(false) }
            .subscribe({
                viewState.openVideoPlayerDialog(
                    VideoPlayerParams(
                        videoUri = it.path,
                        isLocalVideo = false
                    )
                )
            }, {
                it.printStackTrace()
                it.printStackTrace()
                it.handleError { stringResourceId ->
                    viewState.openErrorDialog(titleStringId = stringResourceId)
                }
            }).addTo(compositeDisposable)
    }
}