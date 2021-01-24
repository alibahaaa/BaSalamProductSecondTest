package com.basalam.data.entities
import androidx.room.ColumnInfo

data class Vendor(
    @ColumnInfo(name= "VendorName")
    val name: String
)