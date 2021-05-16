package com.elkir.domain.models

import com.elkir.domain.models.base.Model

data class VideosModel(
    val items: ArrayList<VideoItemModel>
) : Model