package com.elkir.scanner.scenes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import com.elkir.scanner.databinding.DialogLoadingBinding

class LoadingDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .create()

        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        return dialog
    }

    companion object {
        const val LOADING_DIALOG_TAG = "LoadingDialog"
    }
}