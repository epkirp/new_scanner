package com.elkir.scanner.scenes.fragments.video_history.recycler_view

import com.elkir.domain.models.VideoItemModel
import com.elkir.scanner.R
import com.elkir.scanner.base.adapter.BaseAdapter
import com.elkir.scanner.base.adapter.BaseViewHolder
import com.elkir.scanner.databinding.ItemVideoHistoryBinding


class VideoHistoryAdapter(private val callback: Callback) :
    BaseAdapter<VideoItemModel, ItemVideoHistoryBinding>() {

    interface Callback {
        fun onVideoClicked(videoItemModel: VideoItemModel)
    }


    override val layoutId = R.layout.item_video_history


    override fun createViewHolder(binding: ItemVideoHistoryBinding): BaseViewHolder<VideoItemModel> {
        return VideoHistoryViewHolder(binding, callback)
    }
}