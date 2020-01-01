package com.rpl.masukerja.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Personality(
    var id: Int? = null,
    var name: String? = null,
    var nickname: String? = null,
    var description: String? = null,
    var characteristic: String? = null,
    var suggestion: String? = null,
    var job: String? = null,
    var partner: String? = null
) : Parcelable