package com.basalam.basalamproduct.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Rating(
    val rating: Double,
    val count: Int
) : Parcelable
