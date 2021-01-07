package com.basalam.basalamproduct.cacheModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
data class CacheProduct(
    @PrimaryKey
    val id: String,
    val name: String,
    @Embedded
    val photo: CachePhoto,
    @Embedded
    val vendor: CacheVendor,
    val weight: Int,
    val price: Int,
    @Embedded
    val rating: CacheRating
)