package com.a65apps.weather.data.core.network.connectionprovider

interface ConnectionProvider {
    fun isInternetAvailable(): Boolean
}
