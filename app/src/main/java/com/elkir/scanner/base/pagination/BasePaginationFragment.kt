package com.elkir.scanner.base.pagination

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elkir.domain.models.base.Model
import com.elkir.scanner.base.BaseFragment
import com.elkir.scanner.base.adapter.BaseAdapter
import com.elkir.scanner.base.pagination.PaginationConstants.PAGE_LIMIT
import com.elkir.scanner.extensions.changeVisibility

abstract class BasePaginationFragment<VB : ViewDataBinding, M : Model> : BaseFragment<VB>(),
    BasePaginationView<M> {

    abstract override val presenter: BasePaginationPresenter<*, *>

    abstract var progressBar: ProgressBar
    abstract var swipeRefreshLayout: SwipeRefreshLayout
    abstract var recyclerView: RecyclerView
    abstract var placeholder: View
    private lateinit var adapter: BaseAdapter<M, VB>


    abstract override fun showInitialItems(model: M)

    abstract override fun addExtraItems(model: M)

    override fun changePreloaderVisibility(state: Boolean) {
        progressBar.changeVisibility(state)
    }

    override fun setUpListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.onSwipeRefresh()
        }
        recyclerView.addOnScrollListener(PaginationScrollListener(PAGE_LIMIT) {
            presenter.onLoadNewPage()
        })
    }

    override fun changePlaceholderVisibility(state: Boolean) {
        placeholder.changeVisibility(state)
    }

    override fun changeRefreshEnabledState(state: Boolean) {
        swipeRefreshLayout.isEnabled = state
    }

    override fun changeRefreshingState(state: Boolean) {
        swipeRefreshLayout.isRefreshing = state
    }

    protected open fun setUpAdapter(adapter: BaseAdapter<M, VB>) {
        this.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }
}