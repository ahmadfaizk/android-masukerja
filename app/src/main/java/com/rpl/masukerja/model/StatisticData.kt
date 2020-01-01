package com.rpl.masukerja.model

import com.google.gson.annotations.SerializedName

data class StatisticData(
    var name: String? = null,

    @SerializedName("jumlah")
    var count: Int? = null
)