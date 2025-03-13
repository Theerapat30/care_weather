package com.trp.core_utils

object MessageUtils {
    fun makeWord(data: String): String{
        return data.first().uppercase()+data.substring(1).lowercase()
    }
}