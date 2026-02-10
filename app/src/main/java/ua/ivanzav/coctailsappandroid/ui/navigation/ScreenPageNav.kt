package ua.ivanzav.coctailsappandroid.ui.navigation

import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataJson

enum class CocktailsPage {
    ALCOHOL,
    NONALCOHOL
}

sealed interface CocktailsAppUiState {
    data class Success(val cocktailModels: List<CocktailsDataJson>, val page: CocktailsPage = CocktailsPage.ALCOHOL): CocktailsAppUiState
    object Error: CocktailsAppUiState
    object Loading: CocktailsAppUiState
}