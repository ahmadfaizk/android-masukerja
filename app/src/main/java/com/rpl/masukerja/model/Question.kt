package com.rpl.masukerja.model

data class Question(
    var name: String? = null,
    var code: String? = null,
    var answers: ArrayList<Answer>? = null
)