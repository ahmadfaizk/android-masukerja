package com.rpl.masukerja.api.response

import com.rpl.masukerja.model.TestResult

data class ListTestResponse(
    var data: ArrayList<TestResult>? = null
)