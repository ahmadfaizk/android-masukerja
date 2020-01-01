package com.rpl.masukerja.model

import com.google.gson.annotations.SerializedName

data class Question(
    var id: Int? = null,

    @SerializedName("question")
    var name: String? = null,

    var code: String? = null,

    @SerializedName("answer")
    var answers: ArrayList<Answer>? = null
)