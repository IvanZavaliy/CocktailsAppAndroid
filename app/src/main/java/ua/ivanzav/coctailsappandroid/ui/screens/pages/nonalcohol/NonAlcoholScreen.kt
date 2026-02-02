package ua.ivanzav.coctailsappandroid.ui.screens.pages.nonalcohol

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.ivanzav.coctailsappandroid.data.model.CocktailsModelJson
import ua.ivanzav.coctailsappandroid.ui.components.CocktailCard
import ua.ivanzav.coctailsappandroid.ui.screens.cocktail.CocktailDetailScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SharedTransitionScope.NonAlcoholCocktailsScreen(
    cocktailModels: List<CocktailsModelJson>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = cocktailModels, key = { model -> model.id}) { model ->
                CocktailCard(
                    model,
                    animatedVisibilityScope = animatedVisibilityScope,
                    modifier = Modifier
                        .clickable { onItemClick(model.image, model.name, model.id) }
                )
            }
        }
    }
}