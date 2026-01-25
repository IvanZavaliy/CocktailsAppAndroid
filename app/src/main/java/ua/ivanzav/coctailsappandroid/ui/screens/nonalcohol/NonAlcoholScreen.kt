package ua.ivanzav.coctailsappandroid.ui.screens.nonalcohol

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NonAlcoholCocktailsScreen(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val nonAlcoholViewModel: NonAlcoholViewModel = viewModel()
        val nonAlcoholUiState: String = nonAlcoholViewModel.nonAlcoholUiState
        Text("Non-alcohol cocktails screen")
        Text(nonAlcoholUiState)
    }
}