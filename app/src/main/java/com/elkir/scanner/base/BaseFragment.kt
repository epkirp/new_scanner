package com.elkir.scanner.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.elkir.scanner.extensions.safeDialogDismiss
import com.elkir.scanner.extensions.safeShow
import com.elkir.scanner.scenes.dialogs.ErrorDialog
import com.elkir.scanner.scenes.dialogs.LoadingDialog
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

    override fun changeLoadingDialogVisibility(isVisible: Boolean) {
        if (isVisible) {
            childFragmentManager.safeShow(LoadingDialog.LOADING_DIALOG_TAG) {
                LoadingDialog().apply {
                    isCancelable = false
                }
            }
        } else {
            childFragmentManager.safeDialogDismiss(LoadingDialog.LOADING_DIALOG_TAG)
        }
    }

    override fun openErrorDialog(titleStringId: Int) {
        childFragmentManager.safeShow(ErrorDialog.ERROR_DIALOG_TAG) {
            ErrorDialog().apply {
                this.textId = titleStringId
            }
        }
    }
}