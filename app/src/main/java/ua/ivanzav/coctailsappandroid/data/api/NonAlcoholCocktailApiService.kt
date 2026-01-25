package ua.ivanzav.coctailsappandroid.data.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://thecocktaildb.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface NonAlcoholCocktailApiService {
    @GET("api/json/v1/1/filter.php?a=Non_Alcoholic")
    suspend fun getPhotos(): String
}

object NonAlcoholCocktailApi {
    val retrofitService : NonAlcoholCocktailApiService by lazy {
        retrofit.create(NonAlcoholCocktailApiService::class.java)
    }
}