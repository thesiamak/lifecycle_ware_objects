package ir.drax.samples.lifecycle.util

import java.util.*

class SampleCode {
    fun igniteTimer(){
        Timer("ring the bell")
            .schedule(object : TimerTask() {
                override fun run() {
                    Ring()
                }

            },2_000, 250)
    }

    private fun Ring() {
        TODO("Not yet implemented")
    }
}