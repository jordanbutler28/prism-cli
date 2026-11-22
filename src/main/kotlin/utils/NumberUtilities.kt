package utils

fun validRange(
    numberToCheck: Int,
    min: Int,
    max: Int,
): Boolean = numberToCheck in min..max

fun isNonNegative(numberToCheck: Int): Boolean = numberToCheck >= 0