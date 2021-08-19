package ir.drax.samples.lifecycle

import android.app.Application
import androidx.databinding.DataBindingUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ir.drax.samples.lifecycle.di.component.DaggerAppComponent
import ir.drax.samples.lifecycle.util.bindings.BindingComponent
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .factory()
            .create(this)
            .inject(this)

        DataBindingUtil.setDefaultComponent(BindingComponent())
    }
}