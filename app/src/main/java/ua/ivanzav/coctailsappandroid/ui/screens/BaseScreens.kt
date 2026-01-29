package ua.ivanzav.coctailsappandroid.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.ivanzav.coctailsappandroid.R
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsAppUiState
import ua.ivanzav.coctailsappandroid.ui.navigation.CocktailsPage
import ua.ivanzav.coctailsappandroid.ui.screens.pages.alcohol.AlcoholCocktailsScreen
import ua.ivanzav.coctailsappandroid.ui.screens.pages.nonalcohol.NonAlcoholCocktailsScreen
import androidx.compose.material3.LoadingIndicator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BaseScreen (
    cocktailsAppUiState: CocktailsAppUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (cocktailsAppUiState) {
        is CocktailsAppUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CocktailsAppUiState.Success ->
            when {
                cocktailsAppUiState.page == CocktailsPage.ALCOHOL ->
                    AlcoholCocktailsScreen(
                        cocktailsAppUiState.cocktailModels,
                        modifier = modifier.fillMaxWidth()
                    )
                cocktailsAppUiState.page == CocktailsPage.NONALCOHOL ->
                    NonAlcoholCocktailsScreen(
                        cocktailsAppUiState.cocktailModels,
                        modifier = modifier.fillMaxWidth()
                    )
            }
        is CocktailsAppUiState.Error -> ErrorScreen(retryAction ,modifier = modifier.fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator( modifier = Modifier.size(200.dp))
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = ""
        )
        Text(text = "Filed to load", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}