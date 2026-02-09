package ua.ivanzav.coctailsappandroid.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import ua.ivanzav.coctailsappandroid.data.repository.NetworkCocktailsAppRepository
import ua.ivanzav.coctailsappandroid.di.AlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.di.CocktailDetailApiService
import ua.ivanzav.coctailsappandroid.di.CocktailSearchApiService
import ua.ivanzav.coctailsappandroid.di.IngredientsFilterApiService
import ua.ivanzav.coctailsappandroid.di.NonAlcoholCocktailApiService

interface CocktailAppContainer {
    val cocktailsAppRepository: CocktailsAppRepository
}

class DefaultCocktailAppContainer : CocktailAppContainer {
    private val baseUrl = "https://thecocktaildb.com"

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitAlcoCocktailService: AlcoholCocktailApiService by lazy {
        retrofit.create(AlcoholCocktailApiService::class.java)
    }

    private val retrofitNonAlcoCocktailService: NonAlcoholCocktailApiService by lazy {
        retrofit.create(NonAlcoholCocktailApiService::class.java)
    }

    private val retrofitCocktailDetailService: CocktailDetailApiService by lazy {
        retrofit.create(CocktailDetailApiService::class.java)
    }

    private val retrofitCocktailSearchService: CocktailSearchApiService by lazy {
        retrofit.create(CocktailSearchApiService::class.java)
    }

    private val retrofitIngredientsFilterService: IngredientsFilterApiService by lazy {
        retrofit.create(IngredientsFilterApiService::class.java)
    }

    override val cocktailsAppRepository: CocktailsAppRepository by lazy {
        NetworkCocktailsAppRepository(
            retrofitAlcoCocktailService,
            retrofitNonAlcoCocktailService,
            retrofitCocktailDetailService,
            retrofitCocktailSearchService,
            retrofitIngredientsFilterService
        )
    }
}