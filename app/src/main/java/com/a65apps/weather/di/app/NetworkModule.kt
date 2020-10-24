@file:Suppress("MagicNumber")

package com.a65apps.weather.di.app

import android.content.Context
import com.a65apps.weather.BuildConfig
import com.a65apps.weather.data.core.network.ConnectionInterceptor
import com.a65apps.weather.data.core.network.LocationApi
import com.a65apps.weather.data.core.network.connectionprovider.AndroidConnectionProvider
import com.a65apps.weather.data.core.network.connectionprovider.ConnectionProvider
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun connectionProvider(context: Context): ConnectionProvider =
        AndroidConnectionProvider(context)

    @Provides
    @Singleton
    @Named("geo")
    fun provideOkHttp(connectionProvider: ConnectionProvider): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor(ConnectionInterceptor(connectionProvider))
        builder.addInterceptor(
            Interceptor {
                val original = it.request()
                val originalUrl = original.url
                val url = originalUrl.newBuilder()
                    .addQueryParameter(
                        BuildConfig.GEOCODING_QUERY_KEY,
                        BuildConfig.GEOCODING_API_KEY
                    )
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                it.proceed(requestBuilder.build())
            }
        )

        return builder
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideApi(
        @Named("geo") okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): LocationApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GEOCODING_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(LocationApi::class.java)
}
