package nl.rabobank.kotlinmovement.recipes.model


data class IngredientResponse(
    val id: Long,
    val name: String,
    val type: IngredientType,
    val weight: Int
)
