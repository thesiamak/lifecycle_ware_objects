package ir.drax.dindinn.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.model.Status
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.util.ResViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor(private val ordersRepository: OrdersRepository): ResViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    fun getOrders() = ordersRepository
            .getOrders()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.status == Status.SUCCESS) {
                    _orders.value = it.data
                }
            }
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

}
