package ua.ivanzav.coctailsappandroid.ui.screens.nonalcohol

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.ivanzav.coctailsappandroid.data.api.NonAlcoholCocktailApi
import java.io.IOException


class NonAlcoholViewModel() : ViewModel() {
    var nonAlcoholUiState: String by mutableStateOf ("")
        private set

    init {
        getNonAlcoholicCocktailsPhoto()
    }

    fun getNonAlcoholicCocktailsPhoto() {
        viewModelScope.launch {
            try {
                val listResult = NonAlcoholCocktailApi.retrofitService.getPhotos()
                nonAlcoholUiState = listResult
            }
            catch (e: IOException) {

            }
        }
    }
}