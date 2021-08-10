package ir.drax.dindinn.di.module

import dagger.Module
import dagger.Provides
import ir.drax.dindinn.db.inspection.OrderDao
import ir.drax.dindinn.network.DDApiService
import ir.drax.dindinn.repository.IngredientsRepository
import ir.drax.dindinn.repository.IngredientsRepositoryImpl
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.repository.OrdersRepositoryImpl
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