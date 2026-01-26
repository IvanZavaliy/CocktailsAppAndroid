package ua.ivanzav.coctailsappandroid.ui.screens.nonalcohol

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
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
import java.io.IOException

class NonAlcoholViewModel(private val cocktailsAppRepository: CocktailsAppRepository) : ViewModel() {
    var nonAlcoholUiState: CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set

    init {
        getNonAlcoholicCocktailModels()
    }

    fun getNonAlcoholicCocktailModels() {
        viewModelScope.launch {
            nonAlcoholUiState = try {
                val response = cocktailsAppRepository.getNonAlcoholCocktailsModels()
                val listResult = response.drinks
                CocktailsAppUiState.Success(listResult, CocktailsPage.NONALCOHOL)
            } catch (e: IOException) {
                CocktailsAppUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val cocktailsAppRepository = application.container.cocktailsAppRepository
                NonAlcoholViewModel(cocktailsAppRepository = cocktailsAppRepository)
            }
        }
    }
}