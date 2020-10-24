package com.a65apps.weather.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.a65apps.weather.BuildConfig
import com.a65apps.weather.data.city.CityDao
import com.a65apps.weather.data.city.CityEntity
import com.a65apps.weather.data.city.PREDEFINED_CITIES
import java.util.concurrent.Executors

@Database(
    entities = [
        CityEntity::class
    ],
    version = BuildConfig.DB_VERSION
)
abstract class AppDb : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context): AppDb {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDb::class.java,
                BuildConfig.DB_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        getInstance(context).cityDao().insertCities(PREDEFINED_CITIES)
                    }
                }
            }).build()
        }
    }
}
