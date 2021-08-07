package ir.drax.dindinn.network

import io.reactivex.Observable
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.network.model.ServerResponse
import retrofit2.Response
import retrofit2.http.POST


interface DDApiService {

    @POST("v1/orders")
    fun getOrders(): Observable<ServerResponse<List<Order>>>
}