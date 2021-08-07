package ir.drax.dindinn.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.drax.dindinn.di.component.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.ui.SharedViewModel
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class ViewModelModule{

    @Provides
    @Singleton
    fun provideSharedViewModel(ordersRepository: OrdersRepository)=SharedViewModel(ordersRepository)
    
}