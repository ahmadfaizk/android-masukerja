package com.rpl.masukerja.api.response

import com.rpl.masukerja.model.Article

data class ArticleResponse(
    var data: ArrayList<Article>? = null
)