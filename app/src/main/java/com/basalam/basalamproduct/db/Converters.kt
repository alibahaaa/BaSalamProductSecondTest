package com.basalam.basalamproduct.db

import androidx.room.TypeConverter
import com.basalam.basalamproduct.model.Photo
import com.basalam.basalamproduct.model.Rating
import com.basalam.basalamproduct.model.Vendor


/*
*********
*  we convert classes to variable that room can define them .
*********
 */

class Converters {


    //Photo
    @TypeConverter
    fun fromPhoto(photo: Photo): String {
        return photo.url
    }
    @TypeConverter
    fun toPhoto(url: String): Photo {
        return Photo(url)
    }

    //Vendor
    @TypeConverter
    fun fromVendor(vendor: Vendor): String {
        return vendor.name
    }
    @TypeConverter
    fun toVendor(name: String): Vendor {
        return Vendor(name)
    }

    //Rating
    @TypeConverter
    fun fromRating(rating: Rating): Double {
        return rating.rating
    }
    @TypeConverter
    fun toRating(rating: Double): Rating {
        return Rating(rating,rating.toInt())
    }


}