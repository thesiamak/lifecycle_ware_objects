package ir.drax.samples.lifecycle.di.component

import android.content.Context
import ir.drax.samples.lifecycle.di.module.ActivityBuilderModule
import ir.drax.samples.lifecycle.di.module.DataModule
import ir.drax.samples.lifecycle.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.drax.samples.lifecycle.App
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class,
        DataModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AndroidInjector<App>
    }
}