package ua.ivanzav.coctailsappandroid.di

import retrofit2.http.GET
import retrofit2.http.Query
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataResponse
import ua.ivanzav.coctailsappandroid.data.model.IngredientsResponse

interface IngredientsFilterApiService {
    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getIngredientsList(): IngredientsResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): CocktailsDataResponse
}