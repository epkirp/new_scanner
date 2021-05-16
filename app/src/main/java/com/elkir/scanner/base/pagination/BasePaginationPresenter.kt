package com.elkir.scanner.base.pagination

import com.elkir.domain.models.base.Model
import com.elkir.domain.models.base.PaginationModel
import com.elkir.scanner.base.BasePresenter
import com.elkir.scanner.base.pagination.PaginationConstants.PAGE_LIMIT
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class BasePaginationPresenter<M : Model, V : BasePaginationView<M>> : BasePresenter<V>() {

    protected var currentPage = 1
    protected var isLoading = false

    // флаг для отображения плейсхолдера. Если вызван свайп рефреш, то плейсхолдер не будет показываться при ошибке,
    // а будут отображаться прошлые айтемы до загрузки или добавленный ранее плейсхолдер
    protected var isSwipeRefreshCalled = false
    protected lateinit var model: M
    private var isFirstLoad = true
    private var totalCountOfPages = 0


    abstract fun getItems(page: Int, limit: Int): Single<PaginationModel<M>>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.changeRefreshEnabledState(false)
        loadFirstPage(isNeedToShowRefresh = false)
    }

    open fun onSwipeRefresh() {
        if (!isLoading) {
            currentPage = 1
            isFirstLoad = true
            isSwipeRefreshCalled = true
            loadFirstPage(isNeedToShowRefresh = true)
        }
    }

    open fun onChangePlaceholderVisibility(isVisible: Boolean) {
        viewState.changePlaceholderVisibility(isVisible)
    }

    fun onLoadNewPage() {
        if (!isLoading && currentPage <= totalCountOfPages) {
            loadNewPage()
        }
    }

    protected fun onUpdateItems() {
        currentPage = 0
    }

    private fun loadFirstPage(isNeedToShowRefresh: Boolean) {
        getItems(currentPage, PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                changeLoadingState(state = true, isNeedToShowRefresh = isNeedToShowRefresh)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                currentPage++
            }
            .doFinally {
                isSwipeRefreshCalled = false
                viewState.changeRefreshEnabledState(true)
                changeLoadingState(state = false, isNeedToShowRefresh = isNeedToShowRefresh)
            }
            .subscribe({ result ->
                totalCountOfPages = result.countOfPages
                model = result.model
                if (isFirstLoad) {
                    viewState.showInitialItems(model)
                    onChangePlaceholderVisibility(result.totalItems == 0)
                    isFirstLoad = false
                }
            }, {
                if (!isSwipeRefreshCalled) {
                    onChangePlaceholderVisibility(true)
                }
            })
            .addTo(compositeDisposable)
    }

    private fun loadNewPage() {
        getItems(currentPage, PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                changeLoadingState(state = true, isNeedToShowRefresh = false)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                currentPage++
            }
            .doFinally {
                changeLoadingState(state = false, isNeedToShowRefresh = false)
            }
            .subscribe({ result ->
                totalCountOfPages = result.countOfPages
                model = result.model
                viewState.addExtraItems(model)
            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    private fun changeLoadingState(state: Boolean, isNeedToShowRefresh: Boolean) {
        isLoading = state
        if (isNeedToShowRefresh) {
            viewState.changeRefreshingState(state = state)
        } else {
            viewState.changePreloaderVisibility(state = state)
        }
    }
}