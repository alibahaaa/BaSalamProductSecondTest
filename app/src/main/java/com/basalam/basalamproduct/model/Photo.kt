package com.basalam.basalamproduct.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val url: String
) : Parcelable