package ir.drax.samples.lifecycle.util

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleObserver
import java.util.*

class DisplayTimer (context: Context) : LinearLayout(context), LifecycleObserver {

    fun igniteTimer(){
        Timer("ring the bell")
            .schedule(object : TimerTask() {
                override fun run() {
                    Ring()
                }

            },2_000, 250)
    }

    private fun Ring() {
        // ring the bell here
    }
}