package ua.ivanzav.coctailsappandroid.ui.screens.ingredient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import ua.ivanzav.coctailsappandroid.CocktailsApplication
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsAppUiState
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsPage

class IngredientDetailViewModel(private val cocktailsAppRepository: CocktailsAppRepository): ViewModel() {

    var ingredientDetailUiState: CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set

    fun getCocktailsByIngredient(ingredientName: String) {
        viewModelScope.launch {
            ingredientDetailUiState = CocktailsAppUiState.Loading
            try {
                val response = cocktailsAppRepository.filterByIngredient(ingredientName)
                val drinks = response.drinks

                if (drinks.isNotEmpty()) {
                    ingredientDetailUiState = CocktailsAppUiState.Success(drinks, CocktailsPage.ALCOHOL)
                } else {
                    ingredientDetailUiState = CocktailsAppUiState.Error
                }
            } catch (e: Exception) {
                ingredientDetailUiState = CocktailsAppUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val cocktailsAppRepository = application.container.cocktailsAppRepository
                IngredientDetailViewModel(cocktailsAppRepository = cocktailsAppRepository)
            }
        }
    }
}