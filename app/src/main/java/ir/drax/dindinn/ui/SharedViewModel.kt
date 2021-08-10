package ir.drax.dindinn.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import io.reactivex.disposables.Disposable
import ir.drax.dindinn.network.model.Ingredient
import ir.drax.dindinn.repository.IngredientsRepository


class SharedViewModel @Inject constructor(private val ordersRepository: OrdersRepository, private val ingredientsRepository: IngredientsRepository): ResViewModel() {


    val orders = ordersRepository.getAll()

    // Backing field
    private val _ingredients = MutableLiveData<List<Ingredient>>(listOf())
    val ingredients :LiveData<List<Ingredient>> = _ingredients

    fun getIngredients(searchPhrase:String=""):Disposable{
        return (if (searchPhrase.isEmpty())
            ingredientsRepository
                .getAll()
        else
            ingredientsRepository
                .search(searchPhrase)
                .delay(500,TimeUnit.MILLISECONDS))

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resource ->
                    setViewState(resource)

                    if (resource.status==Status.SUCCESS)
                        _ingredients.postValue(resource.data)
                },
                {
                    // no need to handle throwable. it will be handed in Resource
                }
            )
            .also {
                disposable.add(it)
            }
    }

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
        ordersRepository
            .deleteOrder(order)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
