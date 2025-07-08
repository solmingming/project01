// FridgeRepository.kt
package com.example.myapplication_2.data

object FridgeRepository {
    private val fridgeIngredients = mutableSetOf<String>()

    fun getFridge(): Set<String> {
        return fridgeIngredients
    }

    fun addIngredient(ingredient: String) {
        fridgeIngredients.add(ingredient)
    }

    fun removeIngredient(ingredient: String) {
        fridgeIngredients.remove(ingredient)
    }

    fun toggleIngredient(ingredient: String) {
        if (!fridgeIngredients.add(ingredient)) {
            fridgeIngredients.remove(ingredient)
        }
    }

    fun clear() {
        fridgeIngredients.clear()
    }
}
