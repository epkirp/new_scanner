package com.elkir.scanner.extensions

import com.elkir.scanner.R
import retrofit2.HttpException
import java.net.HttpURLConnection

fun Throwable.handleError(
    showErrorInfoAction: (StringResourceId: Int) -> Unit
) {
    if (this is HttpException) {
        when (code()) {
            HttpURLConnection.HTTP_NOT_FOUND -> {
                showErrorInfoAction(R.string.error_video_not_found)
            }
            else -> showErrorInfoAction(R.string.error_unknown)
        }
    } else {
        showErrorInfoAction(R.string.error_unknown)
    }
}