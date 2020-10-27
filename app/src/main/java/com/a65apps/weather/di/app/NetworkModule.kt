@file:Suppress("MagicNumber")

package com.a65apps.weather.di.app

import android.content.Context
import com.a65apps.weather.BuildConfig
import com.a65apps.weather.data.core.network.ConnectionInterceptor
import com.a65apps.weather.data.core.network.LocationApi
import com.a65apps.weather.data.core.network.WeatherApi
import com.a65apps.weather.data.core.network.connectionprovider.AndroidConnectionProvider
import com.a65apps.weather.data.core.network.connectionprovider.ConnectionProvider
import com.a65apps.weather.data.weather.MoshiDateToStringConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val QUALIFIER_GEO = "QUALIFIER_GEO"
private const val QUALIFIER_WEATHER = "QUALIFIER_WEATHER"
private const val QUALIFIER_MOSHI_CONVERTER_FACTORY = "QUALIFIER_MOSHI_CONVERTER_FACTORY"
private const val QUALIFIER_DATE_CONVERTER_FACTORY = "QUALIFIER_DATE_CONVERTER_FACTORY"

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun connectionProvider(context: Context): ConnectionProvider =
        AndroidConnectionProvider(context)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    @Named(QUALIFIER_GEO)
    fun provideGeoOkHttp(
        connectionProvider: ConnectionProvider,
        loggingInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
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
    @Named(QUALIFIER_WEATHER)
    fun provideWeatherOkHttp(
        connectionProvider: ConnectionProvider,
        loggingInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        builder.addInterceptor(ConnectionInterceptor(connectionProvider))
        builder.addInterceptor(
            Interceptor {
                val original = it.request()
                val originalUrl = original.url
                val url = originalUrl.newBuilder()
                    .addQueryParameter(
                        BuildConfig.CLIMACEL_QUERY_KEY,
                        BuildConfig.CLIMACEL_API_KEY
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
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    @Provides
    @Singleton
    @Named(QUALIFIER_MOSHI_CONVERTER_FACTORY)
    fun provideConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    @Named(QUALIFIER_DATE_CONVERTER_FACTORY)
    fun provideDateToStringConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiDateToStringConverterFactory(moshi)

    @Provides
    @Singleton
    fun provideLocationApi(
        @Named(QUALIFIER_GEO) okHttpClient: OkHttpClient,
        @Named(QUALIFIER_MOSHI_CONVERTER_FACTORY) converterFactory: Converter.Factory
    ): LocationApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GEOCODING_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(LocationApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApi(
        @Named(QUALIFIER_WEATHER) okHttpClient: OkHttpClient,
        @Named(QUALIFIER_MOSHI_CONVERTER_FACTORY) moshiConverterFactory: Converter.Factory,
        @Named(QUALIFIER_DATE_CONVERTER_FACTORY) dateConverterFactory: Converter.Factory
    ): WeatherApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.CLIMACEL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .addConverterFactory(dateConverterFactory)
            .build()
            .create(WeatherApi::class.java)
}
