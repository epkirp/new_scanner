package com.elkir.scanner.scenes.fragments.video_history

import com.elkir.scanner.App
import com.elkir.scanner.R
import com.elkir.scanner.base.BaseFragment
import com.elkir.scanner.databinding.FragmentVideoHistoryBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class VideoHistoryHistoryFragment : BaseFragment<FragmentVideoHistoryBinding>(), VideoHistoryView {

    override val layoutId = R.layout.fragment_video_history

    @InjectPresenter
    override lateinit var presenter: VideoHistoryPresenter


    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideVideoHistoryPresenter()

    override fun setUpListeners() {
        //Todo: set listeners
    }
}