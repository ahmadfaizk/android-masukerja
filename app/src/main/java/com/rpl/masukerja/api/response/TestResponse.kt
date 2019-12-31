package com.rpl.masukerja.api.response

import com.google.gson.annotations.SerializedName
import com.rpl.masukerja.model.Question

data class TestResponse(
    @SerializedName("data")
    var questions: ArrayList<Question>? = null
)