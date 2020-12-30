package com.basalam.basalamproduct.db

import androidx.room.TypeConverter
import com.basalam.basalamproduct.model.Photo
import com.basalam.basalamproduct.model.Rating
import com.basalam.basalamproduct.model.Vendor

class Converters {
    @TypeConverter
    fun fromPhoto(photo: Photo): String {
        return photo.url
    }
    @TypeConverter
    fun toPhoto(url: String): Photo {
        return Photo(url)
    }

    @TypeConverter
    fun fromVendor(vendor: Vendor): String {
        return vendor.name
    }
    @TypeConverter
    fun toVendor(name: String): Vendor {
        return Vendor(name)
    }
}