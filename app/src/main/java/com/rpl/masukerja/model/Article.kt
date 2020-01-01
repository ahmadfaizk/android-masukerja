package com.rpl.masukerja.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id: Int? = null,
    var name: String? = null,
    var date: String? = null,
    var category: String? = null,
    var description: String? = null
) : Parcelable