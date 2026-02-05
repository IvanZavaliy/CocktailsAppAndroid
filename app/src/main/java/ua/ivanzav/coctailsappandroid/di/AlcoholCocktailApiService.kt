package ua.ivanzav.coctailsappandroid.di

import retrofit2.http.GET
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataResponse

interface AlcoholCocktailApiService {
    @GET("api/json/v1/1/filter.php?a=Alcoholic")
    suspend fun getModels(): CocktailsDataResponse
}