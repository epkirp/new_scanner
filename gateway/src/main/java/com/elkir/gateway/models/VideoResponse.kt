package com.elkir.gateway.models

import com.elkir.domain.models.VideoItemModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmVideoModel : RealmObject() {
    @PrimaryKey
    var publicKey: String = ""
    var path: String = ""
    var name: String = ""
    var duration: Int = 0
    var lastWatchDate: String = ""
    var isLocalVideo: Boolean = false

    fun toDomain(): VideoItemModel {
        return VideoItemModel(
            path = this.path,
            publicKey = this.publicKey,
            name = this.name,
            duration = this.duration,
            lastWatchDate = this.lastWatchDate,
            isLocalVideo = this.isLocalVideo
        )
    }


    companion object {

        fun fromDomain(domain: VideoItemModel): RealmVideoModel {
            return RealmVideoModel().apply {
                path = domain.path
                publicKey = domain.publicKey
                name = domain.name
                duration = domain.duration
                lastWatchDate = domain.lastWatchDate
                isLocalVideo = domain.isLocalVideo
            }
        }
    }
}