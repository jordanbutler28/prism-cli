import io.github.oshai.kotlinlogging.KotlinLogging
import model.Component
import model.Token
import model.Type
import service.ComponentService
import service.TokenComponentService
import service.TokenService
import utils.isNonNegative
import utils.readNextInt
import utils.readNextLine
import utils.readNonBlankLine
import utils.readValidCategory
import utils.readValidState
import utils.readValidDimension
import utils.readValidType

val tokenService = TokenService()
val componentService = ComponentService()
val tokenComponentService = TokenComponentService()
private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "Prism CLI started" }
    var running = true

    while (running) {
        printMainMenu()
        when (readNextInt("Choose an option: ")) {
            1 -> tokenMenu()
            //2 -> componentMenu()
            //3 -> associationMenu()
            0 -> {
                running = false
                println("Exiting Prism CLI.")
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}

private fun printMainMenu() {
    println(
        """
        === Prism CLI ===
        1. Manage Tokens
        2. Manage Components
        3. Manage Token–Component Links
        0. Exit
        """.trimIndent(),
    )
}

// --- Token Menu ---

private fun tokenMenu() {
    var inMenu = true
    while (inMenu) {
        println(
            """
            --- Tokens ---
            1. Add Token
            2. List Tokens
            3. Update Token
            4. Delete Token
            0. Back
            """.trimIndent(),
        )
        when (readNextInt("Choose an option: ")) {
            1 -> addToken()
            2 -> listAllTokens()
            3 -> updateToken()
            4 -> deleteToken()
            0 -> inMenu = false
            else -> println("Invalid option. Please try again.")
        }
    }
}

fun addToken() {
    val name = readNonBlankLine("Token name: ")
    if (tokenService.getTokens().any { it.name.equals(name, ignoreCase = true) }) {
        println("A token named '$name' already exists.")
        return
    }

    val rawType = readValidType("Token type: ").trim()
    val type = Type.entries.first { it.display.equals(rawType, ignoreCase = true) }
    val value = readNonBlankLine("Token value: ")

    val token = Token(id = 0, name = name, type = type, value = value)
    tokenService.addToken(token)
    println("Token added with ID ${token.id}.")
}

fun listAllTokens() {
    val tokens = tokenService.getTokens()
    if (tokens.isEmpty()) {
        println("No tokens found.")
        return
    }
    tokens.forEach { println(it) }
}

fun updateToken() {
    val id = readNextInt("Enter token ID to update: ")
    val existing = tokenService.getTokens().firstOrNull { it.id == id }
    if (existing == null) {
        println("Token $id not found.")
        return
    }

    val name = readNonBlankLine("New token name (${existing.name}): ")
    val duplicateName =
        tokenService.getTokens().any {
            it.id != id && it.name.equals(name, ignoreCase = true)
        }
    if (duplicateName) {
        println("Another token named '$name' already exists.")
        return
    }

    val rawType = readValidType("New token type (${existing.type.display}): ").trim()
    val type = Type.entries.first { it.display.equals(rawType, ignoreCase = true) }
    val value = readNonBlankLine("New token value (${existing.value}): ")

    val updated = Token(name = name, type = type, value = value)
    if (tokenService.updateToken(id, updated)) {
        println("Token $id updated.")
    } else {
        println("Token $id not found.")
    }
}

fun deleteToken() {
    val id = readNextInt("Enter token ID to delete: ")
    if (tokenService.deleteToken(id)) {
        tokenComponentService.removeLinksForToken(id)
        println("Token $id deleted and its links cleaned up.")
    } else {
        println("Token $id not found.")
    }
}

