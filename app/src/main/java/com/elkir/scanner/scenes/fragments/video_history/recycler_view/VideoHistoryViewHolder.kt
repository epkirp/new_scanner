package com.elkir.scanner.scenes.fragments.video_history.recycler_view

import com.elkir.domain.models.VideoItemModel
import com.elkir.scanner.R
import com.elkir.scanner.base.adapter.BaseViewHolder
import com.elkir.scanner.databinding.ItemVideoHistoryBinding

class VideoHistoryViewHolder(
    private val binding: ItemVideoHistoryBinding,
    private val callback: VideoHistoryAdapter.Callback
) : BaseViewHolder<VideoItemModel>(binding) {

    private lateinit var item: VideoItemModel


    init {
        binding.root.setOnClickListener {
            callback.onVideoClicked(item)
        }
    }


    override fun bind(item: VideoItemModel) {
        this.item = item

        with(binding) {
            tvVideoName.text = item.name
            tvLastWatchDate.text = item.lastWatchDate
            tvPath.text = item.publicKey
            ivVideoLocation.setImageResource(
                if (item.isLocalVideo) {
                    R.drawable.ic_local_video
                } else {
                    R.drawable.ic_remote_video
                }
            )
        }
    }
}