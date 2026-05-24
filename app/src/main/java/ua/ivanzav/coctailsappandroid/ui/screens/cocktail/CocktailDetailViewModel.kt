package ua.ivanzav.coctailsappandroid.ui.screens.cocktail

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
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataJson
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import ua.ivanzav.coctailsappandroid.data.repository.FavoriteRepository

class CocktailDetailViewModel(
    private val cocktailsAppRepository: CocktailsAppRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    var cocktailDetailUiState: CocktailDetailUiState by mutableStateOf(CocktailDetailUiState.Loading)
        private set

    var isFavorite by mutableStateOf(false)
        private set

    fun getCocktailDetailModel(cocktailId: String, userId: String?) {
        viewModelScope.launch {
            cocktailDetailUiState = try {
                val response = cocktailsAppRepository.getCocktailDetailModel(cocktailId)
                val listResult = response.drinks?: emptyList()
                CocktailDetailUiState.Success(listResult)
            } catch (e: IOException) {
                CocktailDetailUiState.Error
            }
            
            if (userId != null) {
                isFavorite = favoriteRepository.isFavorite(userId, cocktailId)
            }
        }
    }

    fun toggleFavorite(userId: String, cocktail: CocktailsDataJson) {
        viewModelScope.launch {
            if (isFavorite) {
                favoriteRepository.removeFavorite(userId, cocktail.id)
                isFavorite = false
            } else {
                favoriteRepository.addFavorite(userId, cocktail)
                isFavorite = true
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val cocktailsAppRepository = application.container.cocktailsAppRepository
                val favoriteRepository = application.container.favoriteRepository
                CocktailDetailViewModel(
                    cocktailsAppRepository = cocktailsAppRepository,
                    favoriteRepository = favoriteRepository
                )
            }
        }
    }
}