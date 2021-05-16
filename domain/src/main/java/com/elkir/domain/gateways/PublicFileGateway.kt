package com.elkir.domain.gateways

import com.elkir.domain.responses.PublicFileResponse
import io.reactivex.Single

interface PublicFileGateway {

    fun getPublicResources(publicKey: String): Single<PublicFileResponse>
}