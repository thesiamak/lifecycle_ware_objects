package ir.drax.samples.lifecycle.di.module

import dagger.Module
import dagger.Provides
import ir.drax.samples.lifecycle.di.RequestInterceptor
import ir.drax.samples.lifecycle.network.DDApiService
import ir.drax.samples.lifecycle.network.MockInterceptor
import lifecycle.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply { level=HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, requestInterceptor: RequestInterceptor,
                            @Named("MockInterceptor") mockInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(requestInterceptor)

            if (BuildConfig.DEBUG)
                addInterceptor(mockInterceptor)

        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMainApiService(retrofit: Retrofit): DDApiService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    @Named("MockInterceptor")
    fun provideMockInterceptor(): Interceptor = MockInterceptor()
}