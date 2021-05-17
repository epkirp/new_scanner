package com.elkir.scanner.scenes.fragments.video_history

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elkir.domain.models.VideoItemModel
import com.elkir.domain.models.VideoPlayerParams
import com.elkir.domain.models.VideosModel
import com.elkir.scanner.App
import com.elkir.scanner.R
import com.elkir.scanner.base.adapter.BaseAdapter
import com.elkir.scanner.base.pagination.BasePaginationFragment
import com.elkir.scanner.databinding.FragmentVideoHistoryBinding
import com.elkir.scanner.scenes.fragments.video_history.recycler_view.VideoHistoryAdapter
import com.elkir.scanner.scenes.fragments.video_player.VideoPlayerDialog
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class VideoHistoryFragment : BasePaginationFragment<FragmentVideoHistoryBinding, VideosModel>(),
    VideoHistoryView {

    override val layoutId = R.layout.fragment_video_history

    @InjectPresenter
    override lateinit var presenter: VideoHistoryPresenter
    override lateinit var progressBar: ProgressBar
    override lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override lateinit var recyclerView: RecyclerView
    override lateinit var placeholder: View

    private val videoHistoryAdapter = VideoHistoryAdapter(object : VideoHistoryAdapter.Callback {
        override fun onVideoClicked(videoItemModel: VideoItemModel) {
            presenter.onVideoClicked(videoItemModel)
        }
    })

    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideVideoHistoryPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = binding.progressBar
        swipeRefreshLayout = binding.swipeRefreshLayout
        recyclerView = binding.recyclerView
        placeholder = binding.tvPlaceholder

        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpAdapter(videoHistoryAdapter as BaseAdapter<VideosModel, FragmentVideoHistoryBinding>)
    }

    override fun addExtraItems(model: VideosModel) {
        videoHistoryAdapter.addExtraItems(model.items)
    }

    override fun showInitialItems(model: VideosModel) {
        videoHistoryAdapter.addNewItems(model.items)
    }

    override fun openVideoPlayerDialog(videoPlayerParams: VideoPlayerParams) {
        VideoPlayerDialog(params = videoPlayerParams).show(
            childFragmentManager,
            VideoPlayerDialog.VIDEO_PLAYER_DIALOG_TAG
        )
    }
}