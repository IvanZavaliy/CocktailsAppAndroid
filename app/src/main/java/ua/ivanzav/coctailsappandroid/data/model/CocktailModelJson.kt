package ua.ivanzav.coctailsappandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailResponse(
    val drinks: List<CocktailModelJson>
)

@Serializable
data class CocktailModelJson(
    @SerialName("strDrink")
    val name: String,

    @SerialName(value = "strDrinkThumb")
    val image: String,

    @SerialName("idDrink")
    val id: String
)
