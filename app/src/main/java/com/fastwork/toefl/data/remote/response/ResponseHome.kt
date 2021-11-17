package com.fastwork.toefl.data.remote.response

import com.fastwork.toefl.data.remote.model.Category

data class ResponseHome(val data: Data? = null)

data class Data(
    val category: List<Category>? = null,
)