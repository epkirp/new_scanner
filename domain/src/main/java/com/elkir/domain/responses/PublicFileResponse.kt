package com.elkir.domain.responses

import com.google.gson.annotations.SerializedName

data class PublicFileResponse(
    @SerializedName("file")
    val path: String,
    val name: String
)