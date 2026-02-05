package ua.ivanzav.coctailsappandroid.ui.screens.cocktail

import ua.ivanzav.coctailsappandroid.data.model.CocktailDetailDataJson

sealed interface CocktailDetailUiState {
    data class Success(val cocktailModelJson: List<CocktailDetailDataJson>): CocktailDetailUiState
    object Error: CocktailDetailUiState
    object Loading: CocktailDetailUiState
}