package ua.ivanzav.coctailsappandroid.ui.screens.nonalcohol

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.ivanzav.coctailsappandroid.data.api.NonAlcoholCocktailApi
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsAppUiState
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsPage
import java.io.IOException

class NonAlcoholViewModel() : ViewModel() {
    var nonAlcoholUiState: CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set

    init {
        getNonAlcoholicCocktailsModels()
    }

    fun getNonAlcoholicCocktailsModels() {
        viewModelScope.launch {
            nonAlcoholUiState = try {
                val response = NonAlcoholCocktailApi.retrofitService.getPhotos()
                val listResult = response.drinks
                CocktailsAppUiState.Success(listResult, CocktailsPage.NONALCOHOL)
            } catch (e: IOException) {
                CocktailsAppUiState.Error
            }
        }
    }
}