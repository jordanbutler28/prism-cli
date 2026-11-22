package service

import model.Component
import model.Category
import model.State

class ComponentService {
    private var components = ArrayList<Component>()
    private var lastId = 0

    private fun getId(): Int = ++lastId

    fun addComponent(component: Component) {
        component.id = getId()
        components.add(component)
    }

    fun getComponents(): List<Component> = components

    fun updateComponent(id: Int, updated: Component) : Boolean {
        val index = components.indexOfFirst { it.id == id }
        if (index != -1) {
            val oldComponent = components[index]
            updated.id = id
            components[index] = updated
            return true
        }
        return false
    }

    fun deleteComponent(id: Int) : Boolean = components.removeIf { it.id == id }

}