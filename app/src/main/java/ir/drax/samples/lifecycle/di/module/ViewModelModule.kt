package ir.drax.samples.lifecycle.di.module

import dagger.Module
import dagger.Provides
import ir.drax.samples.lifecycle.repository.IngredientsRepository
import ir.drax.samples.lifecycle.repository.OrdersRepository
import ir.drax.samples.lifecycle.ui.SharedViewModel
import javax.inject.Singleton

@Module
class ViewModelModule{

    @Provides
    @Singleton
    fun provideSharedViewModel(ordersRepository: OrdersRepository,ingredientsRepository: IngredientsRepository) = SharedViewModel(ordersRepository,ingredientsRepository)
    
}