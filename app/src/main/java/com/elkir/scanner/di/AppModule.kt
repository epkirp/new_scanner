package com.elkir.scanner.di

import com.elkir.scanner.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApp(): App = app
}
