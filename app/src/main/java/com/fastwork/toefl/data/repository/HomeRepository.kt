package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.ParagraphReading
import com.fastwork.toefl.data.remote.response.ResponseHome
import com.fastwork.toefl.utils.AppResult

interface HomeRepository {
    suspend fun getHome(): AppResult<List<ResponseHome>>
    suspend fun getListParagraph(): List<ParagraphReading>
}