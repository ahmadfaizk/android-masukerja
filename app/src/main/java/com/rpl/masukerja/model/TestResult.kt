package com.rpl.masukerja.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestResult(
    var date: String? = null,
    var introvert: Int? = null,
    var extrovert: Int? = null,
    var sensing: Int? = null,
    var intuiting: Int? = null,
    var thingking: Int? = null,
    var feeling: Int? = null,
    var judging: Int? = null,
    var perceiving: Int? = null,
    var result: Personality? = null
): Parcelable