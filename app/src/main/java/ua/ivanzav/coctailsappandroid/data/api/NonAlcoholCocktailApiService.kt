package ua.ivanzav.coctailsappandroid.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import ua.ivanzav.coctailsappandroid.data.model.CocktailResponse

private const val BASE_URL = "https://thecocktaildb.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface NonAlcoholCocktailApiService {
    @GET("api/json/v1/1/filter.php?a=Non_Alcoholic")
    suspend fun getModels(): CocktailResponse
}

interface AlcoholicCocktailApiService {
    @GET("api/json/v1/1/filter.php?a=Alcoholic")
    suspend fun getModels(): CocktailResponse
}

object NonAlcoholCocktailApi {
    val retrofitService : NonAlcoholCocktailApiService by lazy {
        retrofit.create(NonAlcoholCocktailApiService::class.java)
    }
}

object AlcoholCocktailApi {
    val retrofitService : AlcoholicCocktailApiService by lazy {
        retrofit.create(AlcoholicCocktailApiService::class.java)
    }
}