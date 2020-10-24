package com.a65apps.weather.data.city

import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.city.City
import com.a65apps.weather.domain.core.LatLon

object CityEntityMapper : Mapper<CityEntity, City> {
    override fun map(from: CityEntity): City {
        return City(from.id, from.name, LatLon(from.lat, from.lon), from.saved)
    }
}

object CityDtoMapper : Mapper<CityDto, CityEntity> {
    override fun map(from: CityDto): CityEntity {
        return CityEntity(from.id, from.name, from.lat, from.lon)
    }
}

object CityMapper : Mapper<City, CityEntity> {
    override fun map(from: City): CityEntity {
        return CityEntity(
            from.id,
            from.name,
            from.coordinates.lat,
            from.coordinates.lon,
            from.saved
        )
    }
}
