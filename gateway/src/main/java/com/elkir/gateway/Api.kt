package com.elkir.gateway

import com.elkir.domain.responses.PublicFileResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    // https://cloud-api.yandex.net/v1/disk/public/resources?public_key=https://DISK.YANDEX.RU/i/NXdGvxoaMyUrgA
    @GET("/v1/disk/public/resources")
    fun getPublicResources(
        @Query("public_key") publicKey: String
    ): Single<PublicFileResponse>
}