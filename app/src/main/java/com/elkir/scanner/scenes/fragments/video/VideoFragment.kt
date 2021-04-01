package com.elkir.scanner.scenes.fragments.video

import com.elkir.scanner.App
import com.elkir.scanner.R
import com.elkir.scanner.base.BaseFragment
import com.elkir.scanner.databinding.FragmentVideoBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class VideoFragment : BaseFragment<FragmentVideoBinding>(), VideoView {

    override val layoutId = R.layout.fragment_video

    @InjectPresenter
    override lateinit var presenter: VideoPresenter


    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideVideoPresenter()

    override fun setUpListeners() {
        //Todo: set listeners
    }
}