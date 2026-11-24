import io.github.oshai.kotlinlogging.KotlinLogging
import model.Category
import model.State
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
            2 -> componentMenu()
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

// --- Component Menu ---

private fun componentMenu() {
    var inMenu = true
    while (inMenu) {
        println(
            """
            --- Components ---
            1. Add Component
            2. List Components
            3. Update Component
            4. Delete Component
            0. Back
            """.trimIndent(),
        )
        when (readNextInt("Choose an option: ")) {
            1 -> addComponent()
            2 -> listAllComponents()
            3 -> updateComponent()
            4 -> deleteComponent()
            0 -> inMenu = false
            else -> println("Invalid option. Please try again.")
        }
    }
}

fun addComponent() {
    val name = readNonBlankLine("Component name: ")
    if (componentService.getComponents().any { it.name.equals(name, ignoreCase = true) }) {
        println("A component named '$name' already exists.")
        return
    }

    val rawCategory = readValidCategory("Component category: ").trim()
    val category = Category.entries.first { it.display.equals(rawCategory, ignoreCase = true) }

    val rawState = readValidState("Component state: ").trim()
    val state = State.entries.first { it.display.equals(rawState, ignoreCase = true) }

    val minWidth = readValidDimension("Minimum width: ")
    val minHeight = readValidDimension("Minimum height: ")

    val component =
        Component(
            name = name,
            category = category,
            state = state,
            minWidth = minWidth,
            minHeight = minHeight,
        )
    componentService.addComponent(component)
    println("Component added with ID ${component.id}.")
}

fun listAllComponents() {
    val components = componentService.getComponents()
    if (components.isEmpty()) {
        println("No components found.")
        return
    }
    components.forEach { println(it) }
}

fun updateComponent() {
    val id = readNextInt("Enter component ID to update: ")
    val existing = componentService.getComponents().firstOrNull { it.id == id }
    if (existing == null) {
        println("Component $id not found.")
        return
    }

    val name = readNonBlankLine("New component name (${existing.name}): ")
    val duplicateName =
        componentService.getComponents().any {
            it.id != id && it.name.equals(name, ignoreCase = true)
        }
    if (duplicateName) {
        println("Another component named '$name' already exists.")
        return
    }

    val rawCategory = readValidCategory("New component category (${existing.category.display}): ").trim()
    val category = Category.entries.first { it.display.equals(rawCategory, ignoreCase = true) }

    val rawState = readValidState("New component state (${existing.state.display}): ").trim()
    val state = State.entries.first { it.display.equals(rawState, ignoreCase = true) }

    val minWidth = readValidDimension("New minimum width (${existing.minWidth}): ")
    val minHeight = readValidDimension("New minimum height (${existing.minHeight}): ")

    val updated =
        Component(
            name = name,
            category = category,
            state = state,
            minWidth = minWidth,
            minHeight = minHeight,
        )
    if (componentService.updateComponent(id, updated)) {
        println("Component $id updated.")
    } else {
        println("Component $id not found.")
    }
}

fun deleteComponent() {
    val id = readNextInt("Enter component ID to delete: ")
    if (componentService.deleteComponent(id)) {
        tokenComponentService.removeLinksForComponent(id)
        println("Component $id deleted and its links cleaned up.")
    } else {
        println("Component $id not found.")
    }
}

