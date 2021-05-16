package com.elkir.gateway.gateways_impl

import com.elkir.domain.gateways.PublicFileGateway
import com.elkir.domain.responses.PublicFileResponse
import com.elkir.gateway.Api
import io.reactivex.Single

class RetrofitPublicFileGateway(private val api: Api) : PublicFileGateway {

    override fun getPublicResources(publicKey: String): Single<PublicFileResponse> =
        api.getPublicResources(publicKey)
}