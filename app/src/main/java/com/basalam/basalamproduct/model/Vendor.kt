package com.basalam.basalamproduct.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vendor(
    val name: String
) : Parcelable