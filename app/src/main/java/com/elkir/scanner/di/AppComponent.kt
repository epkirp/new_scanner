package com.elkir.scanner.di

import com.elkir.scanner.App
import com.elkir.scanner.scenes.fragments.scanner.ScannerPresenter
import com.elkir.scanner.scenes.fragments.scanner.show_video.ShowVideoPresenter
import com.elkir.scanner.scenes.fragments.video.VideoPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component//(modules = [GatewayModule::class])

interface AppComponent {

    fun inject(target: App)

    fun provideScannerPresenter(): ScannerPresenter
    fun provideShowVideoPresenter(): ShowVideoPresenter

    fun provideVideoPresenter(): VideoPresenter
}