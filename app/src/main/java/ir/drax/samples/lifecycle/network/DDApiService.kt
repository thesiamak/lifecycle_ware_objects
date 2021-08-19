package ir.drax.samples.lifecycle.network

import io.reactivex.Observable
import ir.drax.samples.lifecycle.network.model.Ingredient
import ir.drax.samples.lifecycle.network.model.Order
import ir.drax.samples.lifecycle.network.model.ServerResponse
import retrofit2.http.POST
import retrofit2.http.Query


interface DDApiService {

    @POST("v1/orders")
    fun getOrders(): Observable<ServerResponse<List<Order>>>

    @POST("v1/ingredients")
    fun getIngredients(): Observable<ServerResponse<List<Ingredient>>>

    @POST("v1/ingredients/search")
    fun searchIngredients(@Query("query") query:String): Observable<ServerResponse<List<Ingredient>>>
}