package ua.ivanzav.coctailsappandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailsDataResponse(
    val drinks: List<CocktailsDataJson>
)

@Serializable
data class CocktailsDataJson(
    @SerialName("strDrink")
    val name: String,

    @SerialName(value = "strDrinkThumb")
    val image: String,

    @SerialName("idDrink")
    val id: String
)
