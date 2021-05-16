package com.elkir.gateway.gateways_impl

import com.elkir.domain.gateways.VideoHistoryGateway
import com.elkir.domain.models.VideoItemModel
import com.elkir.domain.models.VideosModel
import com.elkir.domain.models.base.PaginationModel
import com.elkir.gateway.models.RealmVideoModel
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm

class RealmVideoHistoryGateway(private val realm: Realm, private val scheduler: Scheduler) :
    VideoHistoryGateway {

    override fun getVideos(page: Int, limit: Int) = Single.defer {
        realm.beginTransaction()
        val totalItems: Int
        val videos = realm.where(RealmVideoModel::class.java)
            .findAll()
            .also { states -> totalItems = states.size }
            .drop((page - 1) * limit)
            .take(limit)
            .map(RealmVideoModel::toDomain)

        realm.commitTransaction()

        val paginationModel = PaginationModel(
            model = VideosModel(items = videos as ArrayList<VideoItemModel>),
            itemsPerPage = limit,
            countOfPages = totalItems / limit,
            totalItems = totalItems
        )

        Single.just(paginationModel)
    }.subscribeOn(scheduler)

    override fun addVideo(video: VideoItemModel) = Completable.fromAction {
        realm.executeTransaction {
            it.insertOrUpdate(RealmVideoModel.fromDomain(video))
        }
        Completable.complete()
    }.subscribeOn(scheduler)
}