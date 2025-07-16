package com.yllielshani.twocentsdemo.utils

/**
 * Formats an integer amount by inserting dots as thousands separators.
 * Example: 16232444 becomes "16.232.444"
 *
 * @param amount The integer amount to format.
 * @return The formatted string.
 */
fun formatAmountWithDots(amount: Int): String {
    val amountString = amount.toString()
    val stringBuilder = StringBuilder()
    val length = amountString.length

    for (i in length - 1 downTo 0) {
        stringBuilder.append(amountString[i])
        if ((length - 1 - i + 1) % 3 == 0 && i != 0) {
            stringBuilder.append('.')
        }
    }

    return stringBuilder.reverse().toString()
}