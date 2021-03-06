package com.a65apps.weather.presentation.core.navigation

import androidx.fragment.app.Fragment
import com.a65apps.weather.presentation.core.navigation.tab.TabsContainerFragment
import com.a65apps.weather.presentation.forecast.ForecastFragment
import com.a65apps.weather.presentation.forecast.ForecastParams
import com.a65apps.weather.presentation.locationsearch.LocationSearchFragment
import com.a65apps.weather.presentation.realtimedetails.RealtimeDetailsFragment
import com.a65apps.weather.presentation.realtimedetails.RealtimeDetailsParams
import com.a65apps.weather.presentation.realtimeweather.RealtimeWeatherFragment
import com.a65apps.weather.presentation.settings.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object TabsContainer : SupportAppScreen() {
        override fun getFragment(): Fragment = TabsContainerFragment()
    }

    object LocationSearch : SupportAppScreen() {
        override fun getFragment(): Fragment = LocationSearchFragment.newInstance()
    }

    object Settings : SupportAppScreen() {
        override fun getFragment(): Fragment = SettingsFragment.newInstance()
    }

    object RealtimeWeather : SupportAppScreen() {
        override fun getFragment(): Fragment = RealtimeWeatherFragment.newInstance()
    }

    class RealtimeDetails(private val params: RealtimeDetailsParams) : SupportAppScreen() {
        override fun getFragment(): Fragment = RealtimeDetailsFragment.newInstance(params)
    }

    class Forecast(private val params: ForecastParams) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForecastFragment.newInstance(params)
    }
}
