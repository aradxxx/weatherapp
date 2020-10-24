package com.a65apps.weather.presentation.settings

import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.core.navigation.Screens
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    router: AppRouter
) : BaseViewModel<SettingsState>(
    SettingsState,
    router
) {
    fun locationsClicked() {
        router.navigateTo(Screens.LocationSearch)
    }
}
