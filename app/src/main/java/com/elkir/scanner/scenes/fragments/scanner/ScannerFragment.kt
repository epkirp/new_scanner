package com.elkir.scanner.scenes.fragments.scanner

import android.os.Bundle
import android.view.View
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.elkir.domain.models.VideoPlayerParams
import com.elkir.scanner.App
import com.elkir.scanner.R
import com.elkir.scanner.base.BaseFragment
import com.elkir.scanner.databinding.FragmentScannerBinding
import com.elkir.scanner.scenes.fragments.containers.BottomNavigationContainer
import com.elkir.scanner.scenes.fragments.video_player.VideoPlayerDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class ScannerFragment : BaseFragment<FragmentScannerBinding>(), ScannerView {

    override val layoutId = R.layout.fragment_scanner
    private lateinit var codeScanner: CodeScanner

    @InjectPresenter
    override lateinit var presenter: ScannerPresenter


    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideScannerPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        codeScanner = CodeScanner(requireContext(), binding.codeScannerView)
        (requireParentFragment().parentFragment as BottomNavigationContainer)
            .changeBottomNavigationVisibility(isVisible = true)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun setUpListeners() {
        codeScanner.decodeCallback = DecodeCallback {
            presenter.onCodeScanned(it.text)
        }
        codeScanner.errorCallback = ErrorCallback {
            presenter.onCodeScannedWithError(it.message)
        }

        binding.codeScannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun openVideoPlayerDialog(videoPlayerParams: VideoPlayerParams) {
        VideoPlayerDialog(params = videoPlayerParams).show(
            childFragmentManager,
            VideoPlayerDialog.VIDEO_PLAYER_DIALOG_TAG
        )
    }
}