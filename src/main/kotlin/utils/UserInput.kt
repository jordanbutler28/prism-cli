package utils

fun readNextInt(prompt: String = ""): Int {
    while (true) {
        print(prompt)
        val input = readlnOrNull() ?: return 0
        val value = input.trim().toIntOrNull()
        if (value != null) {
            return value
        }
        println("Please enter a valid integer")
    }
}
fun readNextLine(prompt: String = ""): String {
    print(prompt)
    return readlnOrNull() ?: ""
}

fun readNonBlankLine(prompt: String = ""): String {
    while (true) {
        val input = readNextLine(prompt).trim()
        if (input.isNotEmpty()) {
            return input
        }
        println("Please enter a non-empty value.")
    }
}
