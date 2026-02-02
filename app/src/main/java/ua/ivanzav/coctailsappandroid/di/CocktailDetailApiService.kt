package ua.ivanzav.coctailsappandroid.di

import retrofit2.http.GET
import retrofit2.http.Query
import ua.ivanzav.coctailsappandroid.data.model.CocktailDataResponse

interface CocktailDetailApiService {
    @GET("api/json/v1/1/lookup.php")
    suspend fun getModels(@Query("i") cocktailId: String): CocktailDataResponse
}