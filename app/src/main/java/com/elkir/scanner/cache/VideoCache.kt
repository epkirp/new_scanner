package com.elkir.scanner.cache

import android.content.Context
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object VideoCache {

    private var cache: SimpleCache? = null
    private const val maxCacheSize: Long = 100 * 1024 * 1024


    fun getInstance(context: Context): SimpleCache {
        if (cache == null) {
            cache = SimpleCache(
                File(context.cacheDir, "media"),
                LeastRecentlyUsedCacheEvictor(maxCacheSize)
            )
        }
        return cache as SimpleCache
    }
}