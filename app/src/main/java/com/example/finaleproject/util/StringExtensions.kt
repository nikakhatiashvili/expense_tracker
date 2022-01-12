package com.example.finaleproject.util

import java.text.DecimalFormat

    fun Double?.dollarString(): String {
        return this?.let {
            val numberFormat = DecimalFormat("#,##0.00")
            "US$ ${numberFormat.format(this)}"
        } ?: ""
    }
    fun Double?.gellString(): String {
        return this?.let {
            val numberFormat = DecimalFormat("#,##0.0000")
            "GEL₾ ${numberFormat.format(this)}"
        } ?: ""
    }
    fun String?.trimParanthesis(): String {
        return this?.replace(Regex("[()]"), "") ?: ""
    }


