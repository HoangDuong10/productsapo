package com.example.product.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object NumberUtil {
    fun Double.formatNumber(): String {
        val formatter = NumberFormat.getInstance(Locale.US)
        formatter.maximumFractionDigits = 3
        formatter.roundingMode = RoundingMode.DOWN
        return formatter.format(this)
    }

    fun Double.formatNoTrailingZero1(): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = ".".single()
        symbols.groupingSeparator = ",".single()

        val formatter = DecimalFormat()
        formatter.maximumFractionDigits = 12
        formatter.decimalFormatSymbols = symbols
        formatter.isGroupingUsed = true
        return formatter.format(this)

    }

}
