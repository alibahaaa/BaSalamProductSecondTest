package com.basalam.basalamproduct.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Vendor(
    val name: String
) : Parcelable