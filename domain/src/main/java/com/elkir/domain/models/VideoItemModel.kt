package com.elkir.domain.models

import com.elkir.domain.models.base.Model

data class VideoItemModel(
    val path: String,
    val publicKey: String,
    val name: String,
    val duration: Int,
    val isLocalVideo: Boolean,
    val lastWatchDate: String
) : Model