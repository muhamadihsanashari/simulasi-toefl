package com.nalandya.base.data.remote.response

import com.nalandya.base.data.local.model.Product
import com.nalandya.base.data.remote.model.Category

data class ResponseHome(val data: Data? = null)

data class Data(
    val category: List<Category>? = null,
    val productPromo: List<Product>? = null
)