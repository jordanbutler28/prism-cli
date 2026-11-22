package utils

import model.Category
import model.State
import model.Type

fun readValidCategory(prompt: String): String {
    var input = readNextLine(prompt)
    do {
        if (isValidCategory(input)) {
            return input
        } else {
            println("Category does not exist.  You gave $input")
            println("Category options are: ${categories.joinToString(", ")}. Please try again.")
            input = readNextLine(prompt)
        }
    } while (true)
}

fun readValidState(prompt: String): String {
    var input = readNextLine(prompt)
    do {
        if (isValidState(input)) {
            return input
        } else {
            println("State does not exist.  You gave $input")
            println("State options are: ${states.joinToString(", ")}. Please try again.")
            input = readNextLine(prompt)
        }
    } while (true)
}

fun readValidType(prompt: String): String {
    var input = readNextLine(prompt)
    do {
        if (isValidType(input)) {
            return input
        } else {
            println("Type does not exist. You gave $input")
            println("Type options are: ${types.joinToString(", ")}. Please try again.")
            input = readNextLine(prompt)
        }
    } while (true)
}

fun readValidDimension(prompt: String = "Enter dimension (px): "): Int {
    while (true) {
        val input = readNextInt(prompt)
        if (isNonNegative(input)) {
            return input
        }
        println("Dimension cannot be negative. You entered: $input")
    }
}