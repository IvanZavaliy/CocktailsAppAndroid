package ua.ivanzav.coctailsappandroid.ui.screens.cocktail

import ua.ivanzav.coctailsappandroid.data.model.CocktailDataJson

sealed interface CocktailDetailUiState {
    data class Success(val cocktailModelJson: CocktailDataJson): CocktailDetailUiState
    object Error: CocktailDetailUiState
    object Loading: CocktailDetailUiState
}