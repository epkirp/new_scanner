package com.elkir.scanner.scenes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import com.elkir.scanner.databinding.DialogErrorBinding

class ErrorDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogErrorBinding
    var textId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                textId = getInt(TEXT_ID_BUNDLE)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogErrorBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(binding.root)
            .create()

        dialog.setOnShowListener {
            customizeDialog()
        }

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        return dialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        with(outState) {
            putInt(TEXT_ID_BUNDLE, textId)
        }
    }

    private fun customizeDialog() {
        with(binding) {
            if (textId > 0) {
                tvText.text = getString(textId)
            }

            btnOk.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        private const val TEXT_ID_BUNDLE = "textId"
        const val ERROR_DIALOG_TAG = "ErrorDialog"
    }
}