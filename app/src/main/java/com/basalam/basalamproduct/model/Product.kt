package com.basalam.basalamproduct.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey
    val id: String,
    val name: String,
    val photo: Photo,
    val vendor: Vendor,
    val weight: Int,
    val price: Int,
    val rating: Rating
) : Parcelable
