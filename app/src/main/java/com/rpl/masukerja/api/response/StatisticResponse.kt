package com.rpl.masukerja.api.response

import com.rpl.masukerja.model.StatisticData

data class StatisticResponse(
    var data: ArrayList<StatisticData>? = null
)