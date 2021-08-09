package ir.drax.dindinn.di.component

import android.content.Context
import ir.drax.dindinn.di.module.ActivityBuilderModule
import ir.drax.dindinn.di.module.DataModule
import ir.drax.dindinn.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.drax.dindinn.App
import ir.drax.dindinn.di.module.DatabaseModule
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