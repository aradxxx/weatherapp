package com.a65apps.weather.di.core

interface Mapper<From, To> {
    fun map(from: From): To
    fun map(from: Iterable<From>): List<To> = from.map { map(it) }
}
