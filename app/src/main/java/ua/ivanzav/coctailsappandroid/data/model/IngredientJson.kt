package ua.ivanzav.coctailsappandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class  IngredientsResponse(
    val drinks: List<IngredientJson>
)

@Serializable
data class IngredientJson(
    @SerialName("strIngredient1")
    val name: String
)
