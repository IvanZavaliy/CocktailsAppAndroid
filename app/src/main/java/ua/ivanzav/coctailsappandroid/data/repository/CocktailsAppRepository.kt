package ua.ivanzav.coctailsappandroid.data.repository

import ua.ivanzav.coctailsappandroid.data.model.CocktailResponse
import ua.ivanzav.coctailsappandroid.di.AlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.di.NonAlcoholCocktailApiService

interface CocktailsAppRepository {

    suspend fun getAlcoholCocktailModels(): CocktailResponse
    suspend fun getNonAlcoholCocktailsModels(): CocktailResponse
}

class NetworkCocktailsAppRepository(
    private val alcoholCocktailApiService: AlcoholCocktailApiService,
    private val nonAlcoholCocktailApiService: NonAlcoholCocktailApiService
) : CocktailsAppRepository {
    override suspend fun getAlcoholCocktailModels(): CocktailResponse = alcoholCocktailApiService.getModels()

    override suspend fun getNonAlcoholCocktailsModels(): CocktailResponse = nonAlcoholCocktailApiService.getModels()
}