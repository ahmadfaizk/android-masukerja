package com.rpl.masukerja.api.response

import com.google.gson.annotations.SerializedName
import com.rpl.masukerja.model.Job

data class ListJobResponse(
    @SerializedName("data")
    var jobs: List<Job>? = null
)