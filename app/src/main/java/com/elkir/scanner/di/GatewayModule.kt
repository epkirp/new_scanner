package com.elkir.scanner.di

import com.elkir.domain.gateways.PublicFileGateway
import com.elkir.gateway.Api
import com.elkir.gateway.RetrofitPublicFileGateway
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun providePublicFileGateway(api: Api): PublicFileGateway {
        return RetrofitPublicFileGateway(api)
    }
}