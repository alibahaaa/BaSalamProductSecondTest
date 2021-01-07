package com.basalam.basalamproduct.cacheModel

import androidx.room.ColumnInfo

data class CacheVendor(
    @ColumnInfo(name = "VendorName")
    val name: String
)