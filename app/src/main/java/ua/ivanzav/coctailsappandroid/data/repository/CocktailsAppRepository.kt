package ua.ivanzav.coctailsappandroid.data.repository

import ua.ivanzav.coctailsappandroid.data.api.NonAlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.data.model.CocktailResponse

interface CocktailsAppRepository {
    suspend fun getNonAlcoholCocktailsPhotos(): CocktailResponse
}

class NetworkCocktailsAppRepository(
    private val nonAlcoholCocktailApiService: NonAlcoholCocktailApiService
) : CocktailsAppRepository {
    override suspend fun getNonAlcoholCocktailsPhotos(): CocktailResponse = nonAlcoholCocktailApiService.getModels()
}