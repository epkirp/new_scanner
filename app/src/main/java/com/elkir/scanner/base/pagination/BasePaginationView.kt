package com.elkir.scanner.base.pagination

import com.elkir.domain.models.base.Model
import com.elkir.scanner.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BasePaginationView<M : Model> : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePreloaderVisibility(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshingState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeRefreshEnabledState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePlaceholderVisibility(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showInitialItems(model: M)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addExtraItems(model: M)
}