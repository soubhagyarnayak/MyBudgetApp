package com.snsoft.mybudget

import androidx.room.TypeConverter;
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime().toLong()
    }

    @TypeConverter
    fun fromUUID(value: UUID): String{
        return value.toString()
    }

    @TypeConverter
    fun stringToUUID(uuid:String): UUID{
        return UUID.fromString(uuid)
    }
}
