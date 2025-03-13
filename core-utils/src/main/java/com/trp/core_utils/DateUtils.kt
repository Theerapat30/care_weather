package com.trp.core_utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

object DateUtils {

    fun utcToDate(dateUtc: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(dateUtc, 0, ZoneOffset.UTC)
    }

    fun dateToUtc(date: LocalDateTime): Long{
        return date.toEpochSecond(ZoneOffset.UTC)
    }

    fun isTimeAfterHour(hour: Long, time: LocalTime, specificTime: LocalTime): Boolean{
        return time.isBefore(specificTime) && time.plusHours(hour).isAfter(specificTime)
    }

}