package ir.drax.samples.lifecycle.di.module

import dagger.Module
import dagger.Provides
import ir.drax.samples.lifecycle.db.inspection.OrderDao
import ir.drax.samples.lifecycle.network.DDApiService
import ir.drax.samples.lifecycle.repository.IngredientsRepository
import ir.drax.samples.lifecycle.repository.IngredientsRepositoryImpl
import ir.drax.samples.lifecycle.repository.OrdersRepository
import ir.drax.samples.lifecycle.repository.OrdersRepositoryImpl
import javax.inject.Singleton

@Module
class ReposModule {

    @Provides
    @Singleton
    fun provideOrdersRepo(ddApiService: DDApiService, orderDao: OrderDao): OrdersRepository {
        return OrdersRepositoryImpl(ddApiService, orderDao)
    }

    @Provides
    @Singleton
    fun provideIngredientsRepo(ddApiService: DDApiService): IngredientsRepository {
        return IngredientsRepositoryImpl(ddApiService)
    }
}