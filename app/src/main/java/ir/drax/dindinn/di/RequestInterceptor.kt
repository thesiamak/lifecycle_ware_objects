package ir.drax.dindinn.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val httpUrl = request.url.newBuilder().build()

        request = request
            .newBuilder()
            .url(httpUrl)
            .build()

        return chain.proceed(request)
    }
}