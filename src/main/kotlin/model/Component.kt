package model

enum class Category (
    val display: String
) {
   ACTIONS("Actions"),
   FEEDBACK("Feedback"),
   INPUTS("Inputs"),
   NAVIGATION("Navigation"),
   TYPOGRAPHY("Typography"),
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
    var category:  Category = Category.ACTIONS,
    var state: State = State.DEFAULT,
    var minWidth: Int = 0,
    var minHeight: Int = 0,
)