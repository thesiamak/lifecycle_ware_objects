package ir.drax.samples.lifecycle.repository

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ir.drax.samples.lifecycle.db.order.OrderDao
import ir.drax.samples.lifecycle.model.Resource
import ir.drax.samples.lifecycle.network.DDApiService
import ir.drax.samples.lifecycle.network.model.Order
import javax.inject.Inject

interface OrdersRepository {
    fun updateOrders(): Observable<Resource<List<Order>>>
    fun getAll():LiveData<List<Order>>
    fun deleteOrder(order: Order): Completable
}

open class OrdersRepositoryImpl @Inject
constructor(private val apiService: DDApiService, private val orderDao: OrderDao): OrdersRepository {

    override fun updateOrders(): Observable<Resource<List<Order>>> {
        return apiService
            .getOrders()
            .doOnNext {
                it.data.forEach {newOrder->
                    orderDao.add(newOrder)
                }
            }
            .map {
                Resource.success(it.data)
            }.onErrorReturn { throwable ->
                Resource.error(throwable.message)

            }.subscribeOn(Schedulers.io())
            .startWith(Resource.loading())
    }

    override fun getAll() = orderDao.getAll()

    override fun deleteOrder(order: Order) = orderDao.delete(order)
}