package model

enum class Type (
    val display: String
) {
    COLOUR("Colour"),
    TYPOGRAPHY("Typography"),
    PADDING("Padding"),
    MARGINS("Margins"),
    BORDERS("Border Radius"),
    SHADOW("Shadow"),
}

class Token (
    var id: Int = 0,
    var name: String = "",
    var type: Type = Type.COLOUR,
    var value: String = "",
)