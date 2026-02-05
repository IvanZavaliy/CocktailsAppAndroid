package ua.ivanzav.coctailsappandroid.di

import retrofit2.http.GET
import retrofit2.http.Query
import ua.ivanzav.coctailsappandroid.data.model.CocktailDetailDataResponse

interface CocktailSearchApiService {
    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktails(@Query("s") query: String) : CocktailDetailDataResponse
}