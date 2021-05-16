package com.elkir.gateway

import com.elkir.domain.entities.PublicFileEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    // https://cloud-api.yandex.net/v1/disk/public/resources?public_key=https://yadi.sk/i/jlFfRgq6wiFp4g
    @GET("/v1/disk/public/resources")
    fun getPublicResources(
        @Query("public_key") publicKey: String
    ): Single<PublicFileEntity>
}