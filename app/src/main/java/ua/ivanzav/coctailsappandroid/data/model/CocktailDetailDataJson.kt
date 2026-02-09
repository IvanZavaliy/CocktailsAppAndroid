package ua.ivanzav.coctailsappandroid.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailDetailDataResponse(
    @SerialName("drinks")
    val drinks: List<CocktailDetailDataJson>?
)

@Serializable
data class CocktailDetailDataJson(
    @SerialName("idDrink")
    val idDrink: String? = null,

    @SerialName("strDrink")
    val strDrink: String? = null,

    @SerialName("strDrinkAlternate")
    val strDrinkAlternate: String? = null,

    @SerialName("strTags")
    val strTags: String? = null,

    @SerialName("strVideo")
    val strVideo: String? = null,

    @SerialName("strCategory")
    val strCategory: String? = null,

    @SerialName("strIBA")
    val strIBA: String? = null,

    @SerialName("strAlcoholic")
    val strAlcoholic: String? = null,

    @SerialName("strGlass")
    val strGlass: String? = null,

    @SerialName("strInstructions")
    val strInstructions: String? = null,

    @SerialName("strInstructionsES")
    val strInstructionsES: String? = null,

    @SerialName("strInstructionsDE")
    val strInstructionsDE: String? = null,

    @SerialName("strInstructionsFR")
    val strInstructionsFR: String? = null,

    @SerialName("strInstructionsIT")
    val strInstructionsIT: String? = null,

    @SerialName("strInstructionsZH-HANS")
    val strInstructionsZHHANS: String? = null,

    @SerialName("strInstructionsZH-HANT")
    val strInstructionsZHHANT: String? = null,

    @SerialName("strDrinkThumb")
    val strDrinkThumb: String? = null,

    @SerialName("strIngredient1")
    val strIngredient1: String? = null,
    @SerialName("strIngredient2")
    val strIngredient2: String? = null,
    @SerialName("strIngredient3")
    val strIngredient3: String? = null,
    @SerialName("strIngredient4")
    val strIngredient4: String? = null,
    @SerialName("strIngredient5")
    val strIngredient5: String? = null,
    @SerialName("strIngredient6")
    val strIngredient6: String? = null,
    @SerialName("strIngredient7")
    val strIngredient7: String? = null,
    @SerialName("strIngredient8")
    val strIngredient8: String? = null,
    @SerialName("strIngredient9")
    val strIngredient9: String? = null,
    @SerialName("strIngredient10")
    val strIngredient10: String? = null,
    @SerialName("strIngredient11")
    val strIngredient11: String? = null,
    @SerialName("strIngredient12")
    val strIngredient12: String? = null,
    @SerialName("strIngredient13")
    val strIngredient13: String? = null,
    @SerialName("strIngredient14")
    val strIngredient14: String? = null,
    @SerialName("strIngredient15")
    val strIngredient15: String? = null,

    @SerialName("strMeasure1")
    val strMeasure1: String? = null,
    @SerialName("strMeasure2")
    val strMeasure2: String? = null,
    @SerialName("strMeasure3")
    val strMeasure3: String? = null,
    @SerialName("strMeasure4")
    val strMeasure4: String? = null,
    @SerialName("strMeasure5")
    val strMeasure5: String? = null,
    @SerialName("strMeasure6")
    val strMeasure6: String? = null,
    @SerialName("strMeasure7")
    val strMeasure7: String? = null,
    @SerialName("strMeasure8")
    val strMeasure8: String? = null,
    @SerialName("strMeasure9")
    val strMeasure9: String? = null,
    @SerialName("strMeasure10")
    val strMeasure10: String? = null,
    @SerialName("strMeasure11")
    val strMeasure11: String? = null,
    @SerialName("strMeasure12")
    val strMeasure12: String? = null,
    @SerialName("strMeasure13")
    val strMeasure13: String? = null,
    @SerialName("strMeasure14")
    val strMeasure14: String? = null,
    @SerialName("strMeasure15")
    val strMeasure15: String? = null,

    @SerialName("strImageSource")
    val strImageSource: String? = null,

    @SerialName("strImageAttribution")
    val strImageAttribution: String? = null,

    @SerialName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String? = null,

    @SerialName("dateModified")
    val dateModified: String? = null
) {
    fun getListOfIngredientsAndMeasures(): List<Pair<String, String?>> {
        return listOfNotNull(
            strIngredient1?.let { it to strMeasure1 },
            strIngredient2?.let { it to strMeasure2 },
            strIngredient3?.let { it to strMeasure3 },
            strIngredient4?.let { it to strMeasure4 },
            strIngredient5?.let { it to strMeasure5 },
            strIngredient6?.let { it to strMeasure6 },
            strIngredient7?.let { it to strMeasure7 },
            strIngredient8?.let { it to strMeasure8 },
            strIngredient9?.let { it to strMeasure9 },
            strIngredient10?.let { it to strMeasure10 },
            strIngredient11?.let { it to strMeasure11 },
            strIngredient12?.let { it to strMeasure12 },
            strIngredient13?.let { it to strMeasure13 },
            strIngredient14?.let { it to strMeasure14 },
            strIngredient15?.let { it to strMeasure15 }
        )
    }
}
