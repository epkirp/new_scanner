package com.elkir.domain.models

import java.io.Serializable

class VideoPlayerParams(
    var videoUri: String = "",
    var isLocalVideo: Boolean = false
) : Serializable