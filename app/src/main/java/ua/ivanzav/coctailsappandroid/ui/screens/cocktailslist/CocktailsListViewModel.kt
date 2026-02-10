package ua.ivanzav.coctailsappandroid.ui.screens.cocktailslist

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

class CocktailsListViewModel(
    private val cocktailsAppRepository: CocktailsAppRepository,
    private var currentPage: CocktailsPage) : ViewModel() {
    var cocktailsUiState : CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set

    init {
        if (currentPage == CocktailsPage.ALCOHOL) {
            getAlcoholCocktailModels()
        } else {
            getNonAlcoholCocktailModels()
        }
    }

    fun getAlcoholCocktailModels() {
        viewModelScope.launch {
            cocktailsUiState = try {
                val response = cocktailsAppRepository.getAlcoholCocktailModels()
                val listResult = response.drinks
                CocktailsAppUiState.Success(listResult, CocktailsPage.ALCOHOL)
            } catch (e: IOException) {
                CocktailsAppUiState.Error
            }
        }
    }

    fun getNonAlcoholCocktailModels() {
        viewModelScope.launch {
            cocktailsUiState = try {
                val response = cocktailsAppRepository.getNonAlcoholCocktailsModels()
                val listResult = response.drinks
                CocktailsAppUiState.Success(listResult, CocktailsPage.NONALCOHOL)
            } catch (e: IOException) {
                CocktailsAppUiState.Error
            }
        }
    }

    fun fetchCocktails(ingredient: String?) {
        viewModelScope.launch {
            cocktailsUiState = CocktailsAppUiState.Loading
            try {
                if (ingredient == null) {
                    if (currentPage == CocktailsPage.ALCOHOL) {
                        getAlcoholCocktailModels()
                    } else {
                        getNonAlcoholCocktailModels()
                    }
                } else {
                    val response = cocktailsAppRepository.filterByIngredient(ingredient)

                    val drinks = response.drinks

                    cocktailsUiState = CocktailsAppUiState.Success(
                        cocktailModels = drinks,
                        page = CocktailsPage.ALCOHOL
                    )
                }
            } catch (e: Exception) {
                cocktailsUiState = CocktailsAppUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(
            page: CocktailsPage
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val repository = application.container.cocktailsAppRepository

                CocktailsListViewModel(
                    cocktailsAppRepository = repository,
                    currentPage = page
                )
            }
        }
    }
}