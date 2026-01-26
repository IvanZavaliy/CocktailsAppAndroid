package ua.ivanzav.coctailsappandroid.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class CocktailCardViewModel(imageUrl: String): ViewModel() {
    var cardUiState: CardImageUiState by mutableStateOf(CardImageUiState.Loading)
        private set

    init {
        cardImageInit(imageUrl)
    }

    fun cardImageInit(imageUrl: String) {
        viewModelScope.launch {
            cardUiState = CardImageUiState.Success(imageUrl)
        }
    }

    companion object {
        fun provideFactory(imageUrl: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CocktailCardViewModel(imageUrl)
            }
        }
    }
}