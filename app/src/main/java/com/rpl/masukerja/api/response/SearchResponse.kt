package com.rpl.masukerja.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rpl.masukerja.model.Job
import com.rpl.masukerja.model.JobSearch
import com.rpl.masukerja.model.SearchParams
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("data")
    var data: JobSearch? = null,

    var params: SearchParams? = null
): Parcelable