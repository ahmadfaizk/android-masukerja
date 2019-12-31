package com.rpl.masukerja.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Job(
    val id: Int,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var field: String? = null,
    var source: String? = null,
    var image: String? = null,
    var min_salary: Double? = null,
    var max_salary: Double? = null,
    var posting_date: String? = null,
    var closing_date: String? = null,
    var url: String? = null,
    var description: String? = null
): Parcelable