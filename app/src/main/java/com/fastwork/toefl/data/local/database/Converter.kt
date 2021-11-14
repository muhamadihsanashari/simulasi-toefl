package com.fastwork.toefl.data.local.database

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

class Converter {
    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun dateToTimeStamp(date: Date?) = date?.time

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

}