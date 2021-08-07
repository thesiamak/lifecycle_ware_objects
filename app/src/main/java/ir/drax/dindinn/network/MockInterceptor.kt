package ir.drax.dindinn.network

import android.content.res.Resources
import ir.drax.dindinn.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class MockInterceptor(private val resources:Resources): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            var SUCCESS_CODE = 200
            val path = chain.request().url.pathSegments.joinToString("/")

            val responseString = when(path) {
                "v1/orders"-> String(resources.assets.open("api_responses/orders.json").readBytes())
                else -> "{}"
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        "application/json".toMediaTypeOrNull(),
                        responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }

}
