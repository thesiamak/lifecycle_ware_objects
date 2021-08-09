package ir.drax.dindinn.repository

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ir.drax.dindinn.db.inspection.OrderDao
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.network.DDApiService
import ir.drax.dindinn.network.model.Order
import javax.inject.Inject

interface OrdersRepository {
    fun updateOrders(): Observable<Resource<List<Order>>>
    fun getAll():LiveData<List<Order>>
}

open class OrdersRepositoryImpl @Inject
constructor(private val apiService: DDApiService, private val orderDao: OrderDao): OrdersRepository {

    override fun updateOrders(): Observable<Resource<List<Order>>> {
        return apiService
            .getOrders()
            .map {

                it.data.forEach {newOrder->
                    orderDao.add(newOrder)
                }

                Resource.success(it.data)
            }.onErrorReturn { throwable ->
                Resource.error(throwable.message)

            }.subscribeOn(Schedulers.io())
            .startWith(Resource.loading())
    }

    override fun getAll() = orderDao.getAll()
}