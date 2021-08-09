package ir.drax.dindinn.network

import com.google.gson.Gson
import ir.drax.dindinn.BuildConfig
import ir.drax.dindinn.network.model.Addon
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.network.model.ServerResponse
import ir.drax.dindinn.network.model.Status
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.time.Instant
import java.util.concurrent.TimeUnit

class MockInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            var requestCode = 200
            val path = chain.request().url.pathSegments.joinToString("/")

            val responseString = when(path) {
                "v1/orders"->{
                    val now = Instant.now()
                        .minusMillis(4*60*1000)
                    Gson().toJson(
                        ServerResponse(
                        listOf(
                            Order(0,"Large rice", listOf(
                                Addon(21,"Fried Egg",1)
                            ),1,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(0)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(30)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(5)).toString(),
                            ),

                            Order(0,"Second Large rice", listOf(
                                Addon(22,"Second Fried Egg",1),
                                Addon(22,"Second Chicken",5),
                                Addon(22,"Second Salad",2)
                            ),2,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(15)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(45)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(5)).toString(),
                                ),

                            Order(0,"Third Large rice", listOf(
                                Addon(23,"Third Fried Egg",2),
                                Addon(23,"Third Chicken",1),
                            ),7,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(30)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(65)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(6)).toString(),
                                ),

                            Order(0,"Fourth Large rice", listOf(
                                Addon(23,"Fourth Fried Egg",5),
                                Addon(23,"Fourth Salad",2),
                                Addon(23,"Fourth Chicken",1),
                                Addon(23,"Fourth Noodle",1),
                            ),1,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(45)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(75)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(6)).toString(),
                                ),

                            Order(0,"Fifth Large rice", listOf(
                                Addon(23,"Fifth Fried Egg",6)
                            ),3,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(60)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(100)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(6)).toString(),
                                ),

                            Order(0,"Sixth Large rice", listOf(
                                Addon(23,"Sixth Fried Egg",1)
                            ),2,
                                created_at= now.plusMillis(TimeUnit.SECONDS.toMillis(85)).toString(),
                                alerted_at= now.plusMillis(TimeUnit.SECONDS.toMillis(120)).toString(),
                                expired_at= now.plusMillis(TimeUnit.MINUTES.toMillis(7)).toString(),
                                ),

                        ), Status(true,requestCode,"success"))
                        )
                }
                else -> "{}"
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(requestCode)
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
