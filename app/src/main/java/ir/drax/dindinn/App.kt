package ir.drax.dindinn

import android.app.Application
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.HiltAndroidApp
import ir.drax.dindinn.util.bindings.BindingComponent

@HiltAndroidApp
class App : Application() {
    override fun onCreate(){
        super.onCreate()
        DataBindingUtil.setDefaultComponent(BindingComponent())
    }
}