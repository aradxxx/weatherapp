package com.a65apps.weather.data.location

import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.core.LatLon
import com.a65apps.weather.domain.location.Location

object LocationEntityMapper : Mapper<LocationEntity, Location> {
    override fun map(from: LocationEntity): Location {
        return Location(from.id, from.name, LatLon(from.lat, from.lon), from.savedTimestamp)
    }
}

object LocationDtoMapper : Mapper<LocationDto, LocationEntity> {
    override fun map(from: LocationDto): LocationEntity {
        return LocationEntity(from.id, from.name, from.lat, from.lon)
    }
}

object LocationMapper : Mapper<Location, LocationEntity> {
    override fun map(from: Location): LocationEntity {
        return LocationEntity(
            from.id,
            from.name,
            from.coordinates.lat,
            from.coordinates.lon,
            from.savedTimestamp
        )
    }
}
