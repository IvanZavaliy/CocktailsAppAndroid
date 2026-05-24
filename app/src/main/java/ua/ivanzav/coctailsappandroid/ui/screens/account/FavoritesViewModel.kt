package ua.ivanzav.coctailsappandroid.ui.screens.account

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
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataJson
import ua.ivanzav.coctailsappandroid.data.repository.FavoriteRepository

class FavoritesViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    var favorites by mutableStateOf<List<CocktailsDataJson>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun getFavorites(userId: String) {
        viewModelScope.launch {
            isLoading = true
            favorites = favoriteRepository.getFavorites(userId)
            isLoading = false
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CocktailsApplication)
                val favoriteRepository = application.container.favoriteRepository
                FavoritesViewModel(favoriteRepository = favoriteRepository)
            }
        }
    }
}
