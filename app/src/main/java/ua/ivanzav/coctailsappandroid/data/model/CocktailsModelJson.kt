package ua.ivanzav.coctailsappandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailsResponse(
    val drinks: List<CocktailsModelJson>
)

@Serializable
data class CocktailsModelJson(
    @SerialName("strDrink")
    val name: String,

    @SerialName(value = "strDrinkThumb")
    val image: String,

    @SerialName("idDrink")
    val id: String
)
