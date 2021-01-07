package com.basalam.basalamproduct.util

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
}