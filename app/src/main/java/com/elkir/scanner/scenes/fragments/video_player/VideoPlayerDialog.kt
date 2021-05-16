package com.elkir.scanner.scenes.fragments.video_player

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.elkir.domain.models.VideoPlayerParams
import com.elkir.scanner.BuildConfig
import com.elkir.scanner.R
import com.elkir.scanner.databinding.DialogVideoPlayerBinding
import com.elkir.scanner.extensions.changeVisibility
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File

/**
 * Видеоплеер с поворотами экрана через Акселлерометр. При заблокировованном orientation в манифесте
 * повороты все равно будут отрабатывать.
 */
class VideoPlayerDialog(
    private val params: VideoPlayerParams
) : DialogFragment(), Player.EventListener {

    private lateinit var simpleCache: SimpleCache
    private lateinit var player: SimpleExoPlayer
    private lateinit var binding: DialogVideoPlayerBinding


    init {
        setStyle(STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(Color.BLACK)

        player = ExoPlayerFactory.newSimpleInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_video_player, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            flVideoPlayerContainer.setAspectRatio(VIDEO_ASPECT_RATIO)

            ivClose.setOnClickListener {
                dismiss()
            }

            exoPlayerView.player = player
            loadAndPlayVideo()
        }
    }

    override fun onResume() {
        super.onResume()
        player.addListener(this)
    }

    override fun onPause() {
        super.onPause()
        player.removeListener(this)
        player.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        setStatusBarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.design_default_color_on_primary
            )
        )
        player.release()
        if (!params.isLocalVideo) {
            simpleCache.release()
        }
    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        requireActivity().window.statusBarColor = color
    }

    private fun loadAndPlayVideo() {
        if (params.isLocalVideo) {
            playLocalVideo()
        } else {
            playRemoteVideo()
        }
    }

    private fun playRemoteVideo() {
        initPlayer()

        val extractorsFactory = DefaultExtractorsFactory()

        val mediaSource: MediaSource = ExtractorMediaSource(
            Uri.parse(params.videoUri),
            createDataSourceFactory(),
            extractorsFactory,
            null,
            null
        )

        player.prepare(mediaSource)
        player.playWhenReady = true
    }

    private fun playLocalVideo() {
        initPlayer()

        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), BuildConfig.APPLICATION_ID)
        )

        val mediaSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(Uri.fromFile(File(params.videoUri)))

        player.prepare(mediaSource)
        player.playWhenReady = true
    }

    private fun initPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(requireContext())
        binding.exoPlayerView.player = player
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)

        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> setProgressBarVisibility(true)
            ExoPlayer.STATE_READY -> setProgressBarVisibility(false)
            ExoPlayer.STATE_ENDED -> setProgressBarVisibility(false)
        }
    }

    private fun createDataSourceFactory(): CacheDataSourceFactory {
        // Default parameters, except allowCrossProtocolRedirects is true
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            BuildConfig.APPLICATION_ID,
            null,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true /* allowCrossProtocolRedirects */
        )

        val evictor = LeastRecentlyUsedCacheEvictor(MAX_VIDEO_CACHE_SIZE_IN_BYTES)
        val databaseProvider = ExoDatabaseProvider(requireContext())

        simpleCache =
            SimpleCache(File(requireContext().cacheDir, "media"), evictor, databaseProvider)

        return CacheDataSourceFactory(simpleCache, httpDataSourceFactory)
    }

    private fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.changeVisibility(isVisible)
    }


    companion object {
        private const val VIDEO_ASPECT_RATIO = 16f / 9f
        const val VIDEO_PLAYER_DIALOG_TAG = "videoPlayerDialog"

        // 100 MB
        const val MAX_VIDEO_CACHE_SIZE_IN_BYTES: Long = 100 * 1024 * 1024
    }
}
