package com.elkir.scanner.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import moxy.MvpAppCompatFragment


abstract class BaseFragment<VB : ViewDataBinding> : MvpAppCompatFragment(), BaseView {

    abstract val presenter: BasePresenter<*>
    abstract val layoutId: Int

    protected lateinit var binding: VB


    abstract fun setUpListeners()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }
}