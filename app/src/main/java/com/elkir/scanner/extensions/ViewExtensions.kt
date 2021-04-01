package com.elkir.scanner.extensions

import android.view.View


fun View.changeVisibility(state: Boolean) {
    visibility = if (state) {
        View.VISIBLE
    } else {
        View.GONE
    }
}