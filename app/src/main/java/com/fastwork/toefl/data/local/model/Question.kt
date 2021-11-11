package com.fastwork.toefl.data.local.model

data class Question(
    val message: String? = null,
    val question: String,
    val answer: Int,
    var userAnswer: Int,
    val answerOption: List<String>,
    val audioFileName:String?=null
)
