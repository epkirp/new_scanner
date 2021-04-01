package com.elkir.scanner.scenes.fragments.scanner.show_video

import com.budiyev.android.codescanner.CodeScanner
import com.elkir.scanner.App
import com.elkir.scanner.R
import com.elkir.scanner.base.BaseFragment
import com.elkir.scanner.databinding.FragmentScannerBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class ShowVideoFragment : BaseFragment<FragmentScannerBinding>(), ShowVideoView {

    override val layoutId = R.layout.fragment_show_video
    private lateinit var codeScanner: CodeScanner

    @InjectPresenter
    override lateinit var presenter: ShowVideoPresenter


    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideShowVideoPresenter()

    override fun setUpListeners() {
        TODO("Not yet implemented")
    }
}