package com.a65apps.weather.presentation.util

import com.a65apps.weather.presentation.core.State

inline fun <reified S : State> State.nullOr(): S? =
    if (this is S) {
        this
    } else {
        null
    }
