package com.sehatq.test.data.remote.response

import com.sehatq.test.data.local.model.Product
import com.sehatq.test.data.remote.model.Category

data class ResponseHome(val data: Data? = null)

data class Data(
    val category: List<Category>? = null,
    val productPromo: List<Product>? = null
)