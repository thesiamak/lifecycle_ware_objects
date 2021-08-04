package ir.drax.dindinn.repository

import com.dotin.app.model.LoqateFindResult
import com.dotin.app.model.LoqateRetrieveResult
import com.dotin.app.model.Resource
import com.dotin.app.network.LoqateApiService
import com.dotin.app.network.NonLocalNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

interface LoqateRepository {
    suspend fun getOrders(): Flow<Resource<LoqateFindResult>>
    suspend fun retrieveAddress(addressId:String):  Flow<Resource<LoqateRetrieveResult>>
}

open class LoqateRepositoryImpl @Inject
constructor(private val loqateApiService: LoqateApiService): LoqateRepository {

    override suspend fun findAddress(container: String, text: String)= flow {
        emit(Resource.loading(null))

        val result = object : NonLocalNetworkCall<LoqateFindResult>() {
            override suspend fun createCall(): Response<LoqateFindResult> {
                return loqateApiService.findAddress(container,text)
            }
        }.fetch()

        emit(result)

    }.flowOn(Dispatchers.IO)

    override suspend fun retrieveAddress(addressId: String)= flow {
        emit(Resource.loading(null))

        val result = object : NonLocalNetworkCall<LoqateRetrieveResult>() {
            override suspend fun createCall(): Response<LoqateRetrieveResult> {
                return loqateApiService.retrieveAddress(addressId)
            }
        }.fetch()

        emit(result)

    }.flowOn(Dispatchers.IO)
}