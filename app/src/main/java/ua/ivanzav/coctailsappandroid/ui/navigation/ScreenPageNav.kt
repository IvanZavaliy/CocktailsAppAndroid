package ua.ivanzav.coctailsappandroid.ui.navigation

import ua.ivanzav.coctailsappandroid.data.model.CocktailsModelJson

enum class CocktailsPage {
    ALCOHOL,
    NONALCOHOL
}

sealed interface CocktailsAppUiState {
    data class Success(val cocktailModels: List<CocktailsModelJson>, val page: CocktailsPage): CocktailsAppUiState
    object Error: CocktailsAppUiState
    object Loading: CocktailsAppUiState
}