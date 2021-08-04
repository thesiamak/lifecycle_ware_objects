package ir.drax.dindinn.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.drax.dindinn.BuildConfig
import ir.drax.dindinn.network.DDApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////// MAIN RETROFIT MODULE //////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Provides
    fun buildAuthClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS) // write timeout
            readTimeout(10, TimeUnit.SECONDS) // read timeout
            if (BuildConfig.DEBUG)
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        }.build()
    }

    @Singleton
    @Provides
    fun provideMainRetrofitService(client:OkHttpClient): DDApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DDApiService::class.java)

}

