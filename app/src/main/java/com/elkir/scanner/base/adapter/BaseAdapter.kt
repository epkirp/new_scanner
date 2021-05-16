package com.elkir.scanner.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.elkir.domain.models.base.Model

abstract class BaseAdapter<M : Model, VB : ViewDataBinding> :
    RecyclerView.Adapter<BaseViewHolder<M>>() {

    abstract val layoutId: Int
    protected var models = arrayListOf<M>()
    protected lateinit var binding: VB

    abstract fun createViewHolder(binding: VB): BaseViewHolder<M>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<M> {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<M>, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount() = models.size

    fun addNewItems(items: ArrayList<M>) {
        models = items
        notifyDataSetChanged()
    }

    fun addExtraItems(items: List<M>) {
        val index = this.models.size
        this.models.addAll(items)
        notifyItemRangeInserted(index, items.size)
    }

    fun hideItems() {
        models.clear()
        notifyDataSetChanged()
    }

    fun updateItem(item: M) {
        val indexOfItem = models.indexOfFirst { it === item }
        notifyItemChanged(indexOfItem)
    }

    fun removeItem(item: M) {
        val indexOfItem = models.indexOfFirst { it === item }
        models.remove(item)
        notifyItemRemoved(indexOfItem)
    }
}