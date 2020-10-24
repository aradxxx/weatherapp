package com.a65apps.weather.presentation.core.navigation

import androidx.fragment.app.Fragment
import com.a65apps.weather.presentation.core.navigation.tab.TabsContainerFragment
import com.a65apps.weather.presentation.locationsearch.LocationSearchFragment
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
}
