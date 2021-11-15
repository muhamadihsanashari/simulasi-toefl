package com.fastwork.toefl.data.local.model

import java.io.Serializable

data class ModelFullTest(
    val listeningCorrect: Int? = 0,
    val readingCorrect: Int? = 0,
    val structureCorrect: Int? = 0
) : Serializable
