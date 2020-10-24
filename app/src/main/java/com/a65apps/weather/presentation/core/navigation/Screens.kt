package com.a65apps.weather.presentation.core.navigation

import androidx.fragment.app.Fragment
import com.a65apps.weather.presentation.citysearch.CitySearchFragment
import com.a65apps.weather.presentation.core.navigation.tab.TabsContainerFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object TabsContainer : SupportAppScreen() {
        override fun getFragment(): Fragment = TabsContainerFragment()
    }

    object CitySearch : SupportAppScreen() {
        override fun getFragment(): Fragment = CitySearchFragment.newInstance()
    }
}
