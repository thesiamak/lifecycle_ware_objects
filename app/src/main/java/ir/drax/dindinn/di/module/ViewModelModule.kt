package ir.drax.dindinn.di.module

import dagger.Module
import dagger.Provides
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.ui.SharedViewModel
import javax.inject.Singleton

@Module
class ViewModelModule{

    @Provides
    @Singleton
    fun provideSharedViewModel(ordersRepository: OrdersRepository)=SharedViewModel(ordersRepository)
    
}