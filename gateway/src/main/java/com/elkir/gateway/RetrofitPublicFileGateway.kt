package com.elkir.gateway

import com.elkir.domain.entities.PublicFileEntity
import com.elkir.domain.gateways.PublicFileGateway
import io.reactivex.Single

class RetrofitPublicFileGateway(private val api: Api) : PublicFileGateway {

    override fun getPublicResources(publicKey: String): Single<PublicFileEntity> =
        api.getPublicResources(publicKey)
}