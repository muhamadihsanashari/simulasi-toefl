package com.fastwork.toefl.data.local.model

import java.io.Serializable

data class ModelDirection(
    val category: String? = null,
    val chanceCount: Int? = 0,
) : Serializable
