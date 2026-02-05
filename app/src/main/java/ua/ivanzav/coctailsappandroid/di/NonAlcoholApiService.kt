package ua.ivanzav.coctailsappandroid.di

import retrofit2.http.GET
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataResponse

interface NonAlcoholCocktailApiService {
    @GET("api/json/v1/1/filter.php?a=Non_Alcoholic")
    suspend fun getModels(): CocktailsDataResponse
}