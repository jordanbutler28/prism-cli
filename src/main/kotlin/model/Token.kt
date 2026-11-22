package model

enum class Type (
    val display: String
) {
    COLOUR("Colour"),
    TYPOGRAPHY("Typography"),
    SPACING("Spacing"),
    BORDER_WIDTH("Border Width"),
    BORDER_RADIUS("Border Radius"),
    OPACITY("Opacity"),
    SHADOW("Shadow"),
    OTHER("Other"),
}

class Token (
    var id: Int = 0,
    var name: String = "",
    var type: Type = Type.OTHER,
    var value: String = "",
)