package ir.drax.dindinn.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.network.DDApiService
import ir.drax.dindinn.network.model.Order
import javax.inject.Inject

interface OrdersRepository {
    fun getOrders(): Observable<Resource<List<Order>>>
}

open class OrdersRepositoryImpl @Inject
constructor(private val apiService: DDApiService): OrdersRepository {

    override fun getOrders(): Observable<Resource<List<Order>>> {
        return apiService
            .getOrders()
            .map {
                Resource.success(it.data)

            }.onErrorReturn { throwable ->
                Resource.error(throwable.message)

            }.subscribeOn(Schedulers.io())
            .startWith(Resource.loading())
    }
}