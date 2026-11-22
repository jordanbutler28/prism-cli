package utils

import model.Category
import model.State
import model.Type

val categories = setOf("Actions", "Feedback", "Layouts", "Inputs", "Navigation", "Data Display", "Media", "Overlays", "Other")

fun isValidCategory(categoryToCheck: String?): Boolean =
    categoryToCheck != null && categories.any { it.equals(categoryToCheck.trim(), true) }

val states = setOf("Default", "Active", "Hover", "Disabled")

fun isValidState(stateToCheck: String?): Boolean =
    stateToCheck != null && states.any { it.equals(stateToCheck.trim(), true) }

val types = setOf("Colour", "Typography", "Spacing", "Border Width", "Border Radius", "Opacity", "Shadow", "Other")

fun isValidType(typeToCheck: String?): Boolean =
    typeToCheck != null && types.any { it.equals(typeToCheck.trim(), true) }
