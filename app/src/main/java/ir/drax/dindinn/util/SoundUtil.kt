package ir.drax.dindinn.util

import android.content.Context
import android.media.MediaPlayer
import ir.drax.dindinn.R

class SoundUtil {
    companion object{
        fun playSound(context: Context) {
            with(MediaPlayer.create(context, R.raw.juntos_alert)){
                isLooping = false
                setOnPreparedListener {
                    start()
                }
                setOnCompletionListener {
                    release()
                }
            }
        }
    }

}