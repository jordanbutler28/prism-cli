package service

import model.Component
import model.Category
import model.State
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ComponentServiceTest {
    private lateinit var emptyComponentService: ComponentService
    private lateinit var populatedComponentService: ComponentService
    private lateinit var comp1: Component
    private lateinit var comp2: Component
    private lateinit var comp3: Component
    private lateinit var comp4: Component
    private lateinit var comp5: Component

    @BeforeEach
    fun setUp() {
        comp1 = Component(0, "Primary Button", Category.ACTIONS, State.DEFAULT, 80, 40)
        comp2 = Component(0, "Text Input", Category.INPUTS, State.ACTIVE, 160, 44)
        comp3 = Component(0, "Card Container", Category.LAYOUTS, State.DEFAULT, 240, 120)
        comp4 = Component(0, "Top Nav Bar", Category.NAVIGATION, State.DEFAULT, 320, 56)
        comp5 = Component(0, "Modal Dialog", Category.OVERLAYS, State.DEFAULT, 400, 200)

        emptyComponentService = ComponentService()
        populatedComponentService = ComponentService()

        populatedComponentService.addComponent(comp1)
        populatedComponentService.addComponent(comp2)
        populatedComponentService.addComponent(comp3)
        populatedComponentService.addComponent(comp4)
    }

    @Nested
    inner class AddComponents {
        @Test
        fun `add component to an empty service increases size correctly`() {
            assertEquals(0, emptyComponentService.getComponents().size)
            emptyComponentService.addComponent(comp1)
            assertEquals(1, emptyComponentService.getComponents().size)
        }

        @Test
        fun `add component to a populated service increases size correctly`() {
            assertEquals(4, populatedComponentService.getComponents().size)
            populatedComponentService.addComponent(comp5)
            assertEquals(5, populatedComponentService.getComponents().size)
        }

        @Test
        fun `add component stores correct values`() {
            emptyComponentService.addComponent(comp1)

            val retrieved = emptyComponentService.getComponents().first()
            assertEquals("Primary Button", retrieved.name)
            assertEquals(Category.ACTIONS, retrieved.category)
            assertEquals(State.DEFAULT, retrieved.state)
            assertEquals(80, retrieved.minWidth)
            assertEquals(40, retrieved.minHeight)
        }

        @Test
        fun `add component assigns auto-incrementing ids starting at 1`() {
            emptyComponentService.addComponent(comp1)
            emptyComponentService.addComponent(comp2)

            val components = emptyComponentService.getComponents()
            assertEquals(1, components[0].id)
            assertEquals(2, components[1].id)
        }

        @Test
        fun `ensure ids remain unique after multiple inserts`() {
            val ids = populatedComponentService.getComponents().map { it.id }
            assertEquals(listOf(1, 2, 3, 4), ids)
        }
    }

    @Nested
    inner class GetComponents {
        @Test
        fun `getComponents returns an empty list when service is empty`() {
            assertTrue(emptyComponentService.getComponents().isEmpty())
        }

        @Test
        fun `getComponents returns all components when populated`() {
            val components = populatedComponentService.getComponents()
            assertEquals(4, components.size)
            assertTrue(components.containsAll(listOf(comp1, comp2, comp3, comp4)))
        }
    }

    @Nested
    inner class UpdateComponents {
        @Test
        fun `update existing component returns true`() {
            val updated = Component(0, "Updated Button",  Category.ACTIONS, State.DISABLED, 96, 48)
            assertTrue(populatedComponentService.updateComponent(1, updated))
        }

        @Test
        fun `update non-existing component returns false`() {
            val updated = Component(0, "Missing Component",  Category.ACTIONS, State.DEFAULT, 80, 40)
            assertFalse(populatedComponentService.updateComponent(99, updated))
        }

        @Test
        fun `update empty service returns false`() {
            val updated = Component(0, "Updated Button",  Category.ACTIONS, State.HOVER, 80, 40)
            assertFalse(emptyComponentService.updateComponent(1, updated))
        }

        @Test
        fun `update existing component changes its stored values`() {
            val updated = Component(0, "Secondary Button",  Category.ACTIONS, State.DISABLED, 100, 42)
            populatedComponentService.updateComponent(1, updated)

            val retrieved = populatedComponentService.getComponents().first { it.id == 1 }
            assertEquals("Secondary Button", retrieved.name)
            assertEquals(Category.ACTIONS, retrieved.category)
            assertEquals(State.DISABLED, retrieved.state)
            assertEquals(100, retrieved.minWidth)
            assertEquals(42, retrieved.minHeight)
        }

        @Test
        fun `update existing component does not change number of components`() {
            val sizeBefore = populatedComponentService.getComponents().size
            val updated = Component(0, "Updated Input", Category.INPUTS, State.DEFAULT, 180, 44)

            populatedComponentService.updateComponent(2, updated)

            assertEquals(sizeBefore, populatedComponentService.getComponents().size)
        }

        @Test
        fun `update existing component preserves its id even if updated object has different id`() {
            val updated = Component(999, "Pinned ID Component", Category.LAYOUTS, State.DEFAULT, 200, 100)
            populatedComponentService.updateComponent(3, updated)

            val retrieved = populatedComponentService.getComponents().first { it.id == 3 }
            assertEquals(3, retrieved.id)
            assertEquals("Pinned ID Component", retrieved.name)
        }
    }

    @Nested
    inner class DeleteComponents {
        @Test
        fun `delete existing component returns true`() {
            assertTrue(populatedComponentService.deleteComponent(1))
        }

        @Test
        fun `delete non-existing component returns false`() {
            assertFalse(populatedComponentService.deleteComponent(99))
        }

        @Test
        fun `delete from empty service returns false`() {
            assertFalse(emptyComponentService.deleteComponent(1))
        }

        @Test
        fun `delete from populated service reduces size by one`() {
            val sizeBefore = populatedComponentService.getComponents().size
            populatedComponentService.deleteComponent(3)
            assertEquals(sizeBefore - 1, populatedComponentService.getComponents().size)
        }

        @Test
        fun `delete existing component removes correct component`() {
            populatedComponentService.deleteComponent(2)
            assertFalse(populatedComponentService.getComponents().any { it.id == 2 })
        }

        @Test
        fun `delete one component does not remove other components`() {
            populatedComponentService.deleteComponent(1)
            val remainingIds = populatedComponentService.getComponents().map { it.id }
            assertEquals(listOf(2, 3, 4), remainingIds)
        }
    }
}