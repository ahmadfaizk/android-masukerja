package com.rpl.masukerja.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchParams(
    var title: String? = null,
    var field: String? = null,
    var location: String? = null,
    var salary: Int? = null
): Parcelable