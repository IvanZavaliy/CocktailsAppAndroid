package ua.ivanzav.coctailsappandroid.data.repository

import ua.ivanzav.coctailsappandroid.data.model.CocktailDetailDataResponse
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataResponse
import ua.ivanzav.coctailsappandroid.di.AlcoholCocktailApiService
import ua.ivanzav.coctailsappandroid.di.CocktailDetailApiService
import ua.ivanzav.coctailsappandroid.di.CocktailSearchApiService
import ua.ivanzav.coctailsappandroid.di.NonAlcoholCocktailApiService

interface CocktailsAppRepository {

    suspend fun getAlcoholCocktailModels(): CocktailsDataResponse

    suspend fun getNonAlcoholCocktailsModels(): CocktailsDataResponse

    suspend fun getCocktailDetailModel(id: String): CocktailDetailDataResponse

    suspend fun searchCocktails(query: String): CocktailDetailDataResponse
}

class NetworkCocktailsAppRepository(
    private val alcoholCocktailApiService: AlcoholCocktailApiService,
    private val nonAlcoholCocktailApiService: NonAlcoholCocktailApiService,
    private val cocktailDetailApiService: CocktailDetailApiService,
    private val cocktailSearchApiService: CocktailSearchApiService
) : CocktailsAppRepository {
    override suspend fun getAlcoholCocktailModels(): CocktailsDataResponse = alcoholCocktailApiService.getModels()

    override suspend fun getNonAlcoholCocktailsModels(): CocktailsDataResponse = nonAlcoholCocktailApiService.getModels()

    override suspend fun getCocktailDetailModel(id: String): CocktailDetailDataResponse = cocktailDetailApiService.getModels(id)

    override suspend fun searchCocktails(query: String): CocktailDetailDataResponse = cocktailSearchApiService.searchCocktails(query)
}