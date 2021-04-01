package com.elkir.scanner.scenes.fragments.scanner

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.elkir.scanner.App
import com.elkir.scanner.R
import com.elkir.scanner.base.BaseFragment
import com.elkir.scanner.databinding.FragmentScannerBinding
import com.elkir.scanner.extensions.safeDialogDismiss
import com.elkir.scanner.extensions.safeShow
import com.elkir.scanner.scenes.dialogs.ErrorDialog
import com.elkir.scanner.scenes.dialogs.LoadingDialog
import com.elkir.scanner.scenes.dialogs.LoadingDialog.Companion.LOADING_DIALOG_TAG
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

    override fun openShowVideoFragment(link: String) {
        ScannerFragmentDirections.openShowVideoFragment(videoLink = link)
            .let(findNavController()::navigate)
    }

    override fun changeLoadingDialogVisibility(isVisible: Boolean) {
        if (isVisible) {
            childFragmentManager.safeShow(LOADING_DIALOG_TAG) {
                LoadingDialog().apply {
                    isCancelable = false
                }
            }
        } else {
            childFragmentManager.safeDialogDismiss(LOADING_DIALOG_TAG)
        }
    }

    override fun openErrorDialog(errorDescriptionId: Int) {
        childFragmentManager.safeShow(ErrorDialog.ERROR_DIALOG_TAG) {
            ErrorDialog().apply {
                this.textId = errorDescriptionId
            }
        }
    }
}