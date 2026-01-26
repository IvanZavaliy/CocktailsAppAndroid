package ua.ivanzav.coctailsappandroid.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import ua.ivanzav.coctailsappandroid.data.repository.NetworkCocktailsAppRepository
import ua.ivanzav.coctailsappandroid.di.AlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.di.NonAlcoholCocktailApiService

interface CocktailAppContainer {
    val cocktailsAppRepository: CocktailsAppRepository
}

class DefaultCocktailAppContainer : CocktailAppContainer {
    private val baseUrl = "https://thecocktaildb.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitAlcoCocktailService: AlcoholCocktailApiService by lazy {
        retrofit.create(AlcoholCocktailApiService::class.java)
    }

    private val retrofitNonAlcoCocktailService: NonAlcoholCocktailApiService by lazy {
        retrofit.create(NonAlcoholCocktailApiService::class.java)
    }

    override val cocktailsAppRepository: CocktailsAppRepository by lazy {
        NetworkCocktailsAppRepository(
            retrofitAlcoCocktailService,
            retrofitNonAlcoCocktailService
        )
    }
}