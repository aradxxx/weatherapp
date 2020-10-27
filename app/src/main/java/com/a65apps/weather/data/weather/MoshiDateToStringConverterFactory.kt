package com.a65apps.weather.data.weather

import com.squareup.moshi.Moshi
import com.squareup.moshi.rawType
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.util.*

private const val QUOTE = "\""

class MoshiDateToStringConverterFactory(
    private val moshi: Moshi
) : Converter.Factory() {
    private val dateToStringConverter = Converter<Date, String> {
        val dateString = moshi.adapter(Date::class.java).toJson(it)
        return@Converter if (dateString.startsWith(QUOTE) && dateString.endsWith(QUOTE)) {
            dateString.substring(1, dateString.lastIndex - 1)
        } else {
            dateString
        }
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type.rawType == Date::class.java) {
            return dateToStringConverter
        }
        return null
    }
}
