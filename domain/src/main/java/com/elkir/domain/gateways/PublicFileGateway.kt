package com.elkir.domain.gateways

import com.elkir.domain.entities.PublicFileEntity
import io.reactivex.Single

interface PublicFileGateway {

    fun getPublicResources(publicKey: String): Single<PublicFileEntity>
}