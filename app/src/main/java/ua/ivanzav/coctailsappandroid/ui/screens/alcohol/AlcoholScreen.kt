package ua.ivanzav.coctailsappandroid.ui.screens.alcohol

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.ivanzav.coctailsappandroid.data.model.CocktailModelJson
import ua.ivanzav.coctailsappandroid.ui.components.CocktailCard

@Preview(showBackground = true)
@Composable
fun AlcoholCocktailsScreen(modifier: Modifier = Modifier){
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
            items(16){
                CocktailCard(CocktailModelJson("Cocktail", "https://www.thecocktaildb.com/images/media/drink/xxyywq1454511117.jpg", "123"))
            }
        }
    }
}