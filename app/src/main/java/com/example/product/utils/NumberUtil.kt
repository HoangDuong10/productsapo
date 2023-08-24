package com.example.product.utils

import java.text.NumberFormat
import java.util.*

object NumberUtil {
    fun Double.formatNoTrailingZero(): String {
        val formatter = NumberFormat.getInstance(Locale.US)
        formatter.maximumFractionDigits = 3
        return formatter.format(this)
    }


}
