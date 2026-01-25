package ua.ivanzav.coctailsappandroid.data.repository

import ua.ivanzav.coctailsappandroid.data.api.NonAlcoholCocktailApiService

interface CocktailsAppRepository {
    suspend fun getNonAlcoholCocktailsPhotos(): String
}

class NetworkCocktailsAppRepository(
    private val nonAlcoholCocktailApiService: NonAlcoholCocktailApiService
) : CocktailsAppRepository {
    override suspend fun getNonAlcoholCocktailsPhotos(): String = nonAlcoholCocktailApiService.getPhotos()
}