package com.a65apps.weather.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.a65apps.weather.BuildConfig
import com.a65apps.weather.data.location.LocationDao
import com.a65apps.weather.data.location.LocationEntity
import com.a65apps.weather.data.location.PREDEFINED_LOCATIONS
import com.a65apps.weather.data.weather.ForecastEntity
import com.a65apps.weather.data.weather.RealtimeWeatherEntity
import com.a65apps.weather.data.weather.WeatherDao
import dagger.Lazy
import java.util.concurrent.Executors

@Database(
    entities = [
        LocationEntity::class,
        RealtimeWeatherEntity::class,
        ForecastEntity::class
    ],
    version = BuildConfig.DB_VERSION
)
abstract class AppDb : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        fun buildDatabase(context: Context, locationDao: Lazy<LocationDao>): AppDb {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDb::class.java,
                BuildConfig.DB_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        locationDao.get().insertLocations(PREDEFINED_LOCATIONS)
                    }
                }
            }).build()
        }
    }
}
