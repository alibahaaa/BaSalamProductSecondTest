package com.basalam.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    val id: String,
    val name: String,
    @Embedded
    val photo: Photo,
    @Embedded
    val vendor: Vendor,
    val weight: Int,
    val price: Int,
    @Embedded
    val rating: Rating
)
