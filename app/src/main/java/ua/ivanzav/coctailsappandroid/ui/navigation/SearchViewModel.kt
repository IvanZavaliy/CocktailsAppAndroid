package ua.ivanzav.coctailsappandroid.ui.navigation

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
import ua.ivanzav.coctailsappandroid.data.model.CocktailDetailDataJson
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataJson
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import ua.ivanzav.coctailsappandroid.ui.screens.cocktail.CocktailDetailUiState
import java.io.IOException

class SearchViewModel(private val cocktailsAppRepository: CocktailsAppRepository) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set
    var searchUiState: CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set
    var isSearchExecuted by mutableStateOf(false)
        private set

    private var rawSearchResults: List<CocktailDetailDataJson> = emptyList()
    private var currentTab: BottomNavItems = BottomNavItems.ALCOHOL

    fun updateQuery(newQuery: String) {
        searchQuery = newQuery
    }

    fun updateCategory(newTab: BottomNavItems) {
        currentTab = newTab
        applyFilter()
    }

    fun List<CocktailDetailDataJson>.toCocktailsListModels(): List<CocktailsDataJson> {
        return this.mapNotNull { detail ->
            val id = detail.idDrink ?: return@mapNotNull null
            val name = detail.strDrink ?: return@mapNotNull null

            val image = detail.strDrinkThumb ?: ""

            CocktailsDataJson(
                id = id,
                name = name,
                image = image
            )
        }
    }

    fun resetSearchState() {
        searchQuery = ""
        isSearchExecuted = false
        searchUiState = CocktailsAppUiState.Loading
        rawSearchResults = emptyList()
    }

    fun performSearch() {
        if (searchQuery.isBlank()) return
        isSearchExecuted = true

        viewModelScope.launch {
            searchUiState = CocktailsAppUiState.Loading
            try {
                val response = cocktailsAppRepository.searchCocktails(searchQuery)

                rawSearchResults = response.drinks ?: emptyList()

                applyFilter()

            } catch (e: Exception) {
                searchUiState = CocktailsAppUiState.Error
            }
        }
    }

    private fun applyFilter() {
        if (rawSearchResults.isEmpty()) {
            searchUiState = CocktailsAppUiState.Error
            return
        }
        if (currentTab == BottomNavItems.ACCOUNT) {
            return
        }

        val filteredDetails = rawSearchResults.filter { drink ->
            when (currentTab) {
                BottomNavItems.ALCOHOL -> drink.strAlcoholic == "Alcoholic"
                BottomNavItems.NONALCOHOL -> drink.strAlcoholic != "Alcoholic"
                BottomNavItems.ACCOUNT -> false
            }
        }

        if (filteredDetails.isNotEmpty()) {

            val uiList: List<CocktailsDataJson> = filteredDetails.toCocktailsListModels()

            searchUiState = CocktailsAppUiState.Success(
                cocktailModels = uiList,
                page = CocktailsPage.ALCOHOL
            )
        } else {
            searchUiState = CocktailsAppUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val repository = application.container.cocktailsAppRepository
                SearchViewModel(cocktailsAppRepository = repository)
            }
        }
    }
}