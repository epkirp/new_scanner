package com.elkir.scanner.scenes.fragments.scanner

import com.elkir.scanner.R
import com.elkir.scanner.base.BasePresenter
import com.elkir.scanner.constants.PatternConstants
import moxy.InjectViewState
import java.util.*
import javax.inject.Inject

@InjectViewState
class ScannerPresenter @Inject constructor(

) : BasePresenter<ScannerView>() {

    fun onCodeScanned(link: String) {
        viewState.changeLoadingDialogVisibility(isVisible = true)
        val linkForCheck = link.toLowerCase(Locale.US)

        when {
            PatternConstants.patternLocal.matcher(linkForCheck).matches() -> {
                viewState.openShowVideoFragment(linkForCheck)
            }

            PatternConstants.patternYandex.matcher(linkForCheck).matches() -> {
                viewState.openShowVideoFragment(linkForCheck)
            }

            PatternConstants.patternYandexDisk.matcher(linkForCheck).matches() -> {
                viewState.openShowVideoFragment(linkForCheck)
            }

            else -> {
                viewState.changeLoadingDialogVisibility(isVisible = false)
                viewState.openErrorDialog(R.string.error_not_allowed_url)
            }
        }
    }

    fun onCodeScannedWithError(errorMessage: String?) {

    }

    private fun showError() {

    }
}