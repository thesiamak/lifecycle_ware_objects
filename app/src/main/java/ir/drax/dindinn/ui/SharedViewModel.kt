package ir.drax.dindinn.ui

import io.reactivex.android.schedulers.AndroidSchedulers
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.model.Status
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.util.ResViewModel
import javax.inject.Inject

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

    init {
        if (orders.value==null)
            updateOrders()
    }
}
