package ua.ivanzav.coctailsappandroid.ui.screens.alcohol

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okio.IOException
import ua.ivanzav.coctailsappandroid.data.api.AlcoholCocktailApi
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsAppUiState
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsPage

class AlcoholViewModel : ViewModel() {
    var alcoholUiState : CocktailsAppUiState by mutableStateOf(CocktailsAppUiState.Loading)
        private set

    init {
        getAlcoholCocktailModels()
    }

    fun getAlcoholCocktailModels() {
        viewModelScope.launch {
            alcoholUiState = try {
                val response = AlcoholCocktailApi.retrofitService.getModels()
                val listResult = response.drinks
                CocktailsAppUiState.Success(listResult, CocktailsPage.ALCOHOL)
            } catch (e: IOException) {
                CocktailsAppUiState.Error
            }
        }
    }
}