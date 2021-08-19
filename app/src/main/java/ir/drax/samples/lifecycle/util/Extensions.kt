package ir.drax.samples.lifecycle.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import lifecycle.R
import ir.drax.modal.Modal
import ir.drax.modal.ModalBuilder
import ir.drax.modal.model.MoButton

/** ****************************/
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


/**
 * Display message as SnackBar from the top inside fragments
 * */
fun Fragment.message(message: Int, actionText: String = "", callback: View.OnClickListener? = null){
    message(getString(message), actionText, callback)
}
fun Fragment.message(
    message: String,
    actionText: String = "",
    callback: View.OnClickListener? = null
): ModalBuilder? {
    val modal = Modal.builder(requireActivity()).apply {
        setMessage(message)
        icon = R.drawable.ic_info
        direction = Modal.Direction.TopToBottom
        if(actionText.isNotEmpty())
            setCallback(MoButton(actionText, 0) {
                callback?.onClick(it)
                true
            })

    }.build()
    modal?.show()
    return modal
}