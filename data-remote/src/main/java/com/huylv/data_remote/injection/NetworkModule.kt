package com.huylv.data_remote.injection

import android.content.Context
import com.huylv.data_remote.BuildConfig
import com.huylv.data_remote.ConnectionUtils
import com.huylv.data_remote.Constants
import com.huylv.data_remote.network.flavor.service.FlavorService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val client = OkHttpClient.Builder().readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(Cache(context.cacheDir, Constants.CACHE_SIZE))
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                request = if (ConnectionUtils.checkNetworkEnabled(context)) {
                    request.newBuilder()
                        .header(Constants.CACHE_CONTROL, Constants.TIME_CACHE_ONLINE).build()
                } else {
                    request.newBuilder()
                        .header(Constants.CACHE_CONTROL, Constants.TIME_CACHE_OFFLINE).build()
                }
                chain.proceed(request.newBuilder().build())
            })
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
        }
        return client.build()
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    fun provideFlavorService(retrofit: Retrofit): FlavorService =
        retrofit.create(FlavorService::class.java)
}