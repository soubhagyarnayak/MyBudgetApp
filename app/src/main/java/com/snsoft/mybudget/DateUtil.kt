package com.snsoft.mybudget

import java.util.*

object DateUtil {
    fun findStartOfMonth() : Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,0)
        calendar.set(Calendar.HOUR,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        return calendar.time
    }

    fun findEndOfMonth() : Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR,23)
        calendar.set(Calendar.MINUTE,59)
        calendar.set(Calendar.SECOND,59)
        calendar.set(Calendar.MILLISECOND,999)
        return calendar.time
    }
}