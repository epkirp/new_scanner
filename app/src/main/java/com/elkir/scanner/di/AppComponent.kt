package com.elkir.scanner.di

import com.elkir.scanner.App
import com.elkir.scanner.scenes.fragments.scanner.ScannerPresenter
import com.elkir.scanner.scenes.fragments.video_history.VideoHistoryPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [GatewayModule::class])

interface AppComponent {

    fun inject(target: App)

    fun provideScannerPresenter(): ScannerPresenter
    fun provideVideoHistoryPresenter(): VideoHistoryPresenter
}