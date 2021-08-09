package ir.drax.dindinn.ui

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.model.Status
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.util.ResViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import io.reactivex.Completable




class SharedViewModel @Inject constructor(private val ordersRepository: OrdersRepository): ResViewModel() {


    val orders = ordersRepository.getAll()

    fun updateOrders() = ordersRepository
        .updateOrders()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { resource ->
                setViewState(resource)
            },
            {
                // no need to handle throwable. it will be handed in Resource
            }
        )
        .also {
            disposable.add(it)
        }


    private fun setViewState(resource: Resource<*>?) {
        isLoading.value = resource?.status==Status.LOADING
        isEmpty.value = resource?.status==Status.EMPTY_RESULT
    }

    fun deleteOrder(order: Order){
        Completable.complete()
            .delay(500, TimeUnit.MILLISECONDS)
            .doOnComplete {
                ordersRepository
                    .deleteOrder(order)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
            .subscribe()

    }
}
