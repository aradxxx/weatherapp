package com.a65apps.weather.presentation.main

import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.core.navigation.Screens
import com.a65apps.weather.presentation.core.navigation.tab.Tab
import ru.aradxxx.ciceronetabs.TabCicerone
import javax.inject.Inject

class MainViewModel @Inject constructor(
    cicerone: TabCicerone<AppRouter>,
    restoredState: MainState?
) : BaseViewModel<MainState>(
    restoredState ?: MainState(),
    cicerone.activityRouter()
) {
    init {
        router.newRootScreen(Screens.TabsContainer)
        router.switchTab(Tab.General)
        router.newRootScreen(Tab.Settings, Screens.CitySearch)
    }
}
