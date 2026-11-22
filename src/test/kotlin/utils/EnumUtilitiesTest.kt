package utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EnumUtilitiesTest {
    //is valid category
    @Test
    fun `isValidCategory returns true for valid categories regardless of case or whitespace`() {
        assertTrue(isValidCategory("Actions"))
        assertTrue(isValidCategory("data display"))
        assertTrue(isValidCategory("  NAVIGATION  "))
        assertTrue(isValidCategory(" Other "))
    }

    @Test
    fun `isValidCategory returns false for invalid, blank, or null values`() {
        assertFalse(isValidCategory("InvalidCategory"))
        assertFalse(isValidCategory(""))
        assertFalse(isValidCategory("   "))
        assertFalse(isValidCategory(null))
    }
    //is valid state
    @Test
    fun `isValidState returns true for valid states regardless of case or whitespace`() {
        assertTrue(isValidState("Default"))
        assertTrue(isValidState("hover"))
        assertTrue(isValidState("  DISABLED  "))
        assertTrue(isValidState(" Active "))
    }

    @Test
    fun `isValidState returns false for invalid, blank, or null values`() {
        assertFalse(isValidState("Focused"))
        assertFalse(isValidState(""))
        assertFalse(isValidState("   "))
        assertFalse(isValidState(null))
    }
    // is valid type
    @Test
    fun `isValidType returns true for valid token types regardless of case or whitespace`() {
        assertTrue(isValidType("Colour"))
        assertTrue(isValidType("border radius"))
        assertTrue(isValidType("  SPACING  "))
        assertTrue(isValidType(" Border Width "))
    }

    @Test
    fun `isValidType returns false for invalid, blank, or null values`() {
        assertFalse(isValidType("FontSize"))
        assertFalse(isValidType(""))
        assertFalse(isValidType("   "))
        assertFalse(isValidType(null))
    }
}