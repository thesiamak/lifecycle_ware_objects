package ir.drax.dindinn.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.drax.dindinn.databinding.OrderListItemCountdownBinding
import java.time.Instant
import java.util.concurrent.TimeUnit

class CountdownRunner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onValueChanged: () -> Unit = {}
    val binding = OrderListItemCountdownBinding.inflate(LayoutInflater.from(context), this, true)

    var expiresAt:Long = 0

    val remainingMillis:Long
        get() {
            val offset = expiresAt - System.currentTimeMillis()
            return if(offset > 0) offset
            else -1L
        }

    private var timerDisposable:Disposable?=null

    @SuppressLint("CheckResult")
    fun triggerTimer(){
        val diff = TimeUnit.MILLISECONDS.toSeconds(expiresAt - System.currentTimeMillis())
        if (diff > 0)
            timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(diff + 1)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    println(it)
                    onValueChanged()
                    binding.remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis).toInt()
                }
    }
}

@BindingAdapter("expiresAt")
fun setRemaining(view:CountdownRunner, expiresAt:String?){
    expiresAt?.let {
        view.expiresAt = Instant.parse(it).toEpochMilli()
        view.triggerTimer()
    }
}

@BindingAdapter("remainingMin")
fun setRemainingMin(view: CountdownRunner, value: String?){
    /*value?.let {
        view.remaining = value
    }*/
}

@BindingAdapter("remainingMinAttrChanged")
fun setRemainingMin(view: CountdownRunner, inverseBindingListener: InverseBindingListener){
    view.onValueChanged = {
        inverseBindingListener.onChange()
    }
}

@InverseBindingAdapter(attribute = "remainingMin", event = "remainingMinAttrChanged")
fun remainingMinChangedInverseBinding(view: CountdownRunner):String{
    return view.remainingMillis.let {
        when{
            it <= 0 -> ""
            it < TimeUnit.MINUTES.toMillis(1) -> "${TimeUnit.MILLISECONDS.toSeconds(it).toInt()} Secs"
            it < TimeUnit.MINUTES.toMillis(5).toInt() -> "${TimeUnit.MILLISECONDS.toMinutes(it).toInt()} Mins"
            else -> "> 5 mins"
        }
    }
}