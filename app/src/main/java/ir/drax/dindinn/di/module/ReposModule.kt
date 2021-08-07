package ir.drax.dindinn.di.module

import dagger.Module
import dagger.Provides
import ir.drax.dindinn.network.DDApiService
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.repository.OrdersRepositoryImpl
import javax.inject.Singleton

@Module
class ReposModule {

    @Provides
    @Singleton
    fun provideOrdersRepo(ddApiService: DDApiService): OrdersRepository {
        return OrdersRepositoryImpl(ddApiService)
    }
}