package ua.ivanzav.coctailsappandroid.data.repository

import ua.ivanzav.coctailsappandroid.data.model.CocktailsResponse
import ua.ivanzav.coctailsappandroid.di.AlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.di.NonAlcoholCocktailApiService

interface CocktailsAppRepository {

    suspend fun getAlcoholCocktailModels(): CocktailsResponse
    suspend fun getNonAlcoholCocktailsModels(): CocktailsResponse
}

class NetworkCocktailsAppRepository(
    private val alcoholCocktailApiService: AlcoholCocktailApiService,
    private val nonAlcoholCocktailApiService: NonAlcoholCocktailApiService
) : CocktailsAppRepository {
    override suspend fun getAlcoholCocktailModels(): CocktailsResponse = alcoholCocktailApiService.getModels()

    override suspend fun getNonAlcoholCocktailsModels(): CocktailsResponse = nonAlcoholCocktailApiService.getModels()
}