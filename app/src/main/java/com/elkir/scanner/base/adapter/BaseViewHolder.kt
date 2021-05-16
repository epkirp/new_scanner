package com.elkir.scanner.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<I>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: I)
}