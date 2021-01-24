package com.basalam.domain.entities

data class ProductEntity(
    val id: String,
    val name: String,
    val photo: PhotoEntity,
    val vendor: VendorEntity,
    val weight: Int,
    val price: Int,
    val rating: RatingEntity
)