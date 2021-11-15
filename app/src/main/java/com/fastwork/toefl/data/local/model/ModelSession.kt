package com.fastwork.toefl.data.local.model

import java.io.Serializable

data class ModelSession(val session: String? = null, val dataTest: ModelFullTest? = null) :
    Serializable
