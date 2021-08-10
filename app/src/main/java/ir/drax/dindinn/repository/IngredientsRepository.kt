package ir.drax.dindinn.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.network.DDApiService
import ir.drax.dindinn.network.model.Ingredient
import javax.inject.Inject

interface IngredientsRepository {
    fun getAll():Observable<Resource<List<Ingredient>>>
    fun search(searchPhrase:String):Observable<Resource<List<Ingredient>>>
}

open class IngredientsRepositoryImpl @Inject
constructor(private val apiService: DDApiService): IngredientsRepository {

    override fun getAll(): Observable<Resource<List<Ingredient>>> =
        apiService
            .getIngredients()
            .map {
                Resource.success(it.data)
            }.onErrorReturn { throwable ->
                Resource.error(throwable.message)

            }.subscribeOn(Schedulers.io())
            .startWith(Resource.loading())

    override fun search(searchPhrase: String): Observable<Resource<List<Ingredient>>> = apiService
        .searchIngredients(searchPhrase)
        .map {
            Resource.success(it.data)
        }.onErrorReturn { throwable ->
            Resource.error(throwable.message)

        }.subscribeOn(Schedulers.io())
        .startWith(Resource.loading())

}