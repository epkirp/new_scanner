package com.elkir.scanner.scenes.fragments.scanner

import androidx.core.net.toUri
import com.elkir.domain.gateways.PublicFileGateway
import com.elkir.domain.models.VideoPlayerParams
import com.elkir.scanner.base.BasePresenter
import com.elkir.scanner.constants.PatternConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import java.util.*
import javax.inject.Inject

@InjectViewState
class ScannerPresenter @Inject constructor(
    private val publicFileGateway: PublicFileGateway
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
        }
    }

    fun onCodeScannedWithError(errorMessage: String?) {

    }

    private fun showError() {

    }

    private fun getRemoteVideo(publicKey: String) {
        publicFileGateway.getPublicResources(publicKey)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.changeLoadingDialogVisibility(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { viewState.changeLoadingDialogVisibility(false) }
            .subscribe({
                if (it.file == null) {
                    //todo: show error
                } else {
                    viewState.openVideoPlayerDialog(
                        VideoPlayerParams(
                            videoUri = it.file!!,
                            isLocalVideo = false
                        )
                    )
                }
            }, {
                it.printStackTrace()
            }).addTo(compositeDisposable)
    }
}