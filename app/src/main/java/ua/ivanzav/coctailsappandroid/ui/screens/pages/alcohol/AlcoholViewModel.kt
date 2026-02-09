package ua.ivanzav.coctailsappandroid.ui.screens.pages.alcohol

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
import okio.IOException
import ua.ivanzav.coctailsappandroid.CocktailsApplication
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsAppUiState
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsPage

class AlcoholViewModel(private val cocktailsAppRepository: CocktailsAppRepository) : ViewModel() {
    var alcoholUiState : CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set

    init {
        getAlcoholCocktailModels()
    }

    fun getAlcoholCocktailModels() {
        viewModelScope.launch {
            alcoholUiState = try {
                val response = cocktailsAppRepository.getAlcoholCocktailModels()
                val listResult = response.drinks
                CocktailsAppUiState.Success(listResult, CocktailsPage.ALCOHOL)
            } catch (e: IOException) {
                CocktailsAppUiState.Error
            }
        }
    }

    fun fetchCocktails(ingredient: String?) {
        viewModelScope.launch {
            alcoholUiState = CocktailsAppUiState.Loading // Вмикаємо лоадер
            try {
                if (ingredient == null) {
                    // 1. Якщо фільтру немає - вантажимо стандартний список (Алкогольні)
                    getAlcoholCocktailModels()
                    // (для NonAlcoholViewModel тут буде виклик getNonAlcoholicCocktailModels())
                } else {
                    // 2. Якщо є інгредієнт - фільтруємо по ньому
                    val response = cocktailsAppRepository.filterByIngredient(ingredient)

                    // Перетворення спрощеної моделі в CocktailsDataJson (якщо типи відрізняються)
                    // Якщо filterByIngredient повертає CocktailsDataResponse з CocktailsDataJson, то все ок.
                    // Якщо повертає деталі, використовуйте мапер.
                    val drinks = response.drinks ?: emptyList()

                    alcoholUiState = CocktailsAppUiState.Success(
                        cocktailModels = drinks,
                        page = CocktailsPage.ALCOHOL
                    )
                }
            } catch (e: Exception) {
                alcoholUiState = CocktailsAppUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val cocktailsAppRepository = application.container.cocktailsAppRepository
                AlcoholViewModel(cocktailsAppRepository = cocktailsAppRepository)
            }
        }
    }
}