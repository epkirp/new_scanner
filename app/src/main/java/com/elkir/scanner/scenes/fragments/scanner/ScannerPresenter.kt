package com.elkir.scanner.scenes.fragments.scanner

import androidx.core.net.toUri
import com.elkir.domain.gateways.PublicFileGateway
import com.elkir.domain.gateways.VideoHistoryGateway
import com.elkir.domain.models.VideoItemModel
import com.elkir.domain.models.VideoPlayerParams
import com.elkir.scanner.R
import com.elkir.scanner.base.BasePresenter
import com.elkir.scanner.constants.PatternConstants
import com.elkir.scanner.extensions.handleError
import com.elkir.scanner.utils.getCurrentDateAsString
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import java.util.*
import javax.inject.Inject

@InjectViewState
class ScannerPresenter @Inject constructor(
    private val publicFileGateway: PublicFileGateway,
    private val videoHistoryGateway: VideoHistoryGateway
) : BasePresenter<ScannerView>() {

    fun onCodeScanned(link: String) {
        val linkForCheck = link.toLowerCase(Locale.US)

        when {
            PatternConstants.patternLocal.matcher(linkForCheck).matches() -> {
                viewState.openVideoPlayerDialog(
                    VideoPlayerParams(
                        videoUri = link.toUri().normalizeScheme().toString(),
                        isLocalVideo = true
                    )
                )
            }

            PatternConstants.patternYandex.matcher(linkForCheck).matches() ||
                    PatternConstants.patternYandexDisk.matcher(linkForCheck).matches() -> {

                getRemoteVideo(publicKey = link.toUri().normalizeScheme().toString())
            }

            else -> viewState.openErrorDialog(R.string.error_not_allowed_url)
        }
    }

    fun onCodeScannedWithError(errorMessage: String?) {

    }

    private fun showError() {

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
                it.handleError { stringResourceId ->
                    viewState.openErrorDialog(titleStringId = stringResourceId)
                }
            }).addTo(compositeDisposable)
    }
}