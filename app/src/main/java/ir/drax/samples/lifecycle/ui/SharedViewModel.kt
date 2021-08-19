package ir.drax.samples.lifecycle.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import io.reactivex.disposables.Disposable
import ir.drax.samples.lifecycle.model.Resource
import ir.drax.samples.lifecycle.model.Status
import ir.drax.samples.lifecycle.network.model.Ingredient
import ir.drax.samples.lifecycle.network.model.Order
import ir.drax.samples.lifecycle.repository.IngredientsRepository
import ir.drax.samples.lifecycle.repository.OrdersRepository
import ir.drax.samples.lifecycle.util.BaseViewModel


class SharedViewModel @Inject constructor(private val ordersRepository: OrdersRepository, private val ingredientsRepository: IngredientsRepository): BaseViewModel() {


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

                    if (resource.status== Status.SUCCESS)
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
        .subscribe { resource ->
            println(resource)
            setViewState(resource)
        }
        .also {
            disposable.add(it)
        }


    private fun setViewState(resource: Resource<*>?) {
        isLoading.value = resource?.status==Status.LOADING
        isEmpty.value = resource?.status==Status.EMPTY_RESULT
        isError.value = resource?.status==Status.ERROR
    }

    fun deleteOrder(order: Order){
        ordersRepository
            .deleteOrder(order)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}
