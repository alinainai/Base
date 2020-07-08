package com.gas.app.learn.calendarviewV2

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.UI
import org.jetbrains.anko.frameLayout


/**
 * Fragment基类
 */
open class BaseDialogFragment : DialogFragment() {

    private lateinit var viewGenerator: ViewManager.() -> Unit
    protected var isShowing = false


    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NO_TITLE, 0)
        super.onCreate(savedInstanceState)
    }

    fun generateView(generator: ViewManager.() -> Unit) {
        viewGenerator = generator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = requireActivity().UI {
        frameLayout {
            contentDescription = "RootLayout"
            viewGenerator()
        }
    }.view



    fun showDialog(fragmentManager: FragmentManager?) {
        if (!isShowing) {
            isShowing = true
            fragmentManager?.let {
                show(it, javaClass.simpleName)
            }
        }
    }

    fun hideDialog() {
        if (isShowing) {
            isShowing = false
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShowing = false
    }

    fun setCanceledOnTouchOutside(cancel: Boolean) {
        dialog?.setCanceledOnTouchOutside(cancel)
    }
}