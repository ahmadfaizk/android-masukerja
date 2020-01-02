package com.rpl.masukerja.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JobSearch(
    @SerializedName("data")
    var jobs: List<Job>? = null,

    @SerializedName("current_page")
    var currentPage: Int? = null,

    @SerializedName("last_page")
    var lastPage: Int? = null,

    @SerializedName("first_page_url")
    var firstPageUrl: String? = null,

    @SerializedName("last_page_url")
    var lastPageUrl: String? = null,

    @SerializedName("next_page_url")
    var nextPageUrl: String? = null,

    @SerializedName("prev_page_url")
    var prevPageUrl: String? = null,

    @SerializedName("per_page")
    var perPage: Int? = null,

    var path: String? = null,

    var from: Int? = null,

    var to: Int? = null,

    var total: Int? = null
) : Parcelable