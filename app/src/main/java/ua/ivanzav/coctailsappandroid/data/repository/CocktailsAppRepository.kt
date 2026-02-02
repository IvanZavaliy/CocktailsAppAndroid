package ua.ivanzav.coctailsappandroid.data.repository

import ua.ivanzav.coctailsappandroid.data.model.CocktailDataResponse
import ua.ivanzav.coctailsappandroid.data.model.CocktailsResponse
import ua.ivanzav.coctailsappandroid.di.AlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.di.CocktailDetailApiService
import ua.ivanzav.coctailsappandroid.di.NonAlcoholCocktailApiService

interface CocktailsAppRepository {

    suspend fun getAlcoholCocktailModels(): CocktailsResponse

    suspend fun getNonAlcoholCocktailsModels(): CocktailsResponse

    suspend fun getCocktailDetailModel(id: String): CocktailDataResponse
}

class NetworkCocktailsAppRepository(
    private val alcoholCocktailApiService: AlcoholCocktailApiService,
    private val nonAlcoholCocktailApiService: NonAlcoholCocktailApiService,
    private val cocktailDetailApiService: CocktailDetailApiService
) : CocktailsAppRepository {
    override suspend fun getAlcoholCocktailModels(): CocktailsResponse = alcoholCocktailApiService.getModels()

    override suspend fun getNonAlcoholCocktailsModels(): CocktailsResponse = nonAlcoholCocktailApiService.getModels()

    override suspend fun getCocktailDetailModel(id: String): CocktailDataResponse = cocktailDetailApiService.getModels(id)
}