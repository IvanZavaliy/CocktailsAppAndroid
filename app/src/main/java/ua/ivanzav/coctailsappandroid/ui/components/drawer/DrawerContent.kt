package ua.ivanzav.coctailsappandroid.ui.components.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.ivanzav.coctailsappandroid.data.repository.CocktailsAppRepository
import kotlin.collections.emptyList

@Composable
fun DrawerContent(
    repository: CocktailsAppRepository,
    selectedIngredient: String?,
    onIngredientSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val ingredientsList by produceState<List<String>>(initialValue = emptyList(), key1 = Unit) {
        try {
            val response = repository.getIngredients()

            value = response.drinks.mapNotNull { it.name }.sorted()
        } catch (e: Exception) {
            value = emptyList()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Filter by ingredients",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
        )

        HorizontalDivider(thickness = 2.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onIngredientSelected(null) }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            RadioButton(
                selected = (selectedIngredient == null),
                onClick = { onIngredientSelected(null) }
            )
            Text(
                text = "All Cocktails (Reset)",
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        HorizontalDivider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(ingredientsList) { ingredient ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onIngredientSelected(ingredient) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = (ingredient == selectedIngredient),
                        onClick = { onIngredientSelected(ingredient) }
                    )
                    Text(
                        text = ingredient,
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}