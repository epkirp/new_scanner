package com.elkir.scanner.extensions

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

inline fun FragmentManager.safeShow(tag: String, fragmentBuilder: () -> DialogFragment) {
    val openedDialog = findFragmentByTag(tag)

    if (openedDialog == null) {
        val dialog = fragmentBuilder.invoke()
        dialog.show(this, tag)
    }
}

fun FragmentManager.safeDialogDismiss(tag: String) {
    val openedDialog = findFragmentByTag(tag)
    (openedDialog as? DialogFragment)?.dismiss()
}
