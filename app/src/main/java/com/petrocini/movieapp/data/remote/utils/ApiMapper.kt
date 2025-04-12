package com.petrocini.movieapp.data.remote.utils

interface ApiMapper<Domain, Entity> {
    fun mapToDomain(apiDto: Entity): Domain
}