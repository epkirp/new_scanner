package com.elkir.scanner.di

import com.elkir.domain.gateways.PublicFileGateway
import com.elkir.domain.gateways.VideoHistoryGateway
import com.elkir.gateway.Api
import com.elkir.gateway.gateways_impl.RealmVideoHistoryGateway
import com.elkir.gateway.gateways_impl.RetrofitPublicFileGateway
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.realm.Realm
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun providePublicFileGateway(api: Api): PublicFileGateway {
        return RetrofitPublicFileGateway(api)
    }

    @Provides
    @Singleton
    fun provideVideoHistoryGateway(
        realm: Realm,
        scheduler: Scheduler
    ): VideoHistoryGateway {
        return RealmVideoHistoryGateway(realm, scheduler)
    }
}