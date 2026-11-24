package model

enum class Category (
    val display: String
) {
    ACTIONS("Actions"),
    FEEDBACK("Feedback"),
    LAYOUTS("Layouts"),
    INPUTS("Inputs"),
    NAVIGATION("Navigation"),
    DATA_DISPLAY("Data Display"),
    MEDIA("Media"),
    OVERLAYS("Overlays"),
    OTHER("Other")
}

enum class State (
    val display: String
) {
    DEFAULT("Default"),
    ACTIVE("Active"),
    HOVER("Hover"),
    DISABLED("Disabled"),
}

data class Component (
    var id: Int = 0,
    var name: String = "",
    var category:  Category = Category.OTHER,
    var state: State = State.DEFAULT,
    var minWidth: Int = 0,
    var minHeight: Int = 0,
) {
    override fun toString(): String =
        """
        #$id
        Name: $name
        Category: ${category.display}
        State: ${state.display}
        Min Width: $minWidth
        Min Height: $minHeight
        """.trimIndent() + "\n"
}