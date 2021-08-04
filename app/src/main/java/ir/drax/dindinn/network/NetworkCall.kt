package ir.drax.dindinn.network

import ir.drax.dindinn.model.Resource
import ir.drax.dindinn.model.Status
import ir.drax.dindinn.network.model.ServerResponse
import okhttp3.Headers
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class NetworkCall<ResultType> {
    suspend fun fetch(): Resource<ResultType> {
        return try {
            val response = createCall()
            if (response.isSuccessful) {
                onSuccess(response.body()?.data,response.headers())
                Resource(Status.SUCCESS, response.body()?.data, response.body()?.status?.message)

            } else{
                val errorMsg = response.errorBody()?.let {
                    JSONObject(it.charStream().readText()).getString("message")
                }

                Resource.error(errorMsg?:"" ,null, response.code())
            }

        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.error(errorCode = e.code())

        } catch (e: ConnectException) {
            e.printStackTrace()
            Resource.error(errorCode = ConnectException)

        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            Resource.error(errorCode = SocketTimeoutException)

        }catch (e : UnknownHostException){
            e.printStackTrace()
            Resource.error(errorCode = UnknownHostException)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(errorCode = Exception)
        }
    }

    abstract suspend fun createCall(): Response<ServerResponse<ResultType>>
    open suspend fun onSuccess(result: ResultType?, headers: Headers){}
    companion object{
        final const val ConnectException=600
        final const val SocketTimeoutException=601
        final const val UnknownHostException=602
        final const val Exception=603
    }
}