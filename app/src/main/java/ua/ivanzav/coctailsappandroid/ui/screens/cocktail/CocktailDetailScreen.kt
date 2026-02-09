package ua.ivanzav.coctailsappandroid.ui.screens.cocktail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import ua.ivanzav.coctailsappandroid.data.model.CocktailDetailDataJson

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.CocktailDetailScreen(
    imageUrl: String,
    labelText: String,
    drinkId: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cocktailDetailViewModel: CocktailDetailViewModel = viewModel(
        factory = CocktailDetailViewModel.Factory
    )
    LaunchedEffect(drinkId) {
        cocktailDetailViewModel.getCocktailDetailModel(drinkId)
    }

    val cocktailDetailUiState = cocktailDetailViewModel.cocktailDetailUiState

    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)
        .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 40.dp)
            ) {
                Surface(
                    modifier = modifier
                        .padding(16.dp, end = 16.dp, top = 16.dp)
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "image/$imageUrl"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 300)
                            }
                        ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth(1f)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        GlideImage(
                            model = imageUrl,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .background(color = MaterialTheme.colorScheme.surface)
                        )
                    }
                }

                Column {
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = labelText,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = modifier
                                .padding(top = 16.dp)
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "text/$labelText"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 300)
                                    }
                                )
                        )

                        when(cocktailDetailUiState){
                            is CocktailDetailUiState.Loading -> ContentLoading()
                            is CocktailDetailUiState.Success -> MainDetailContent(cocktailDetailUiState.cocktailModelJson.first())
                            is CocktailDetailUiState.Error -> ContentError()
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp)
                .navigationBarsPadding()
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Go Back"
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MainDetailContent(drink: CocktailDetailDataJson, modifier: Modifier = Modifier) {
    val ingredientsList = drink.getListOfIngredientsAndMeasures()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${drink.strCategory} | ${drink.strGlass}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .padding(8.dp)
        )

        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
        )
        ingredientsList.forEach { (ingredient, nullableMeasure) ->
            val measure: String = if (nullableMeasure == null) ""
            else "$nullableMeasure "

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = "https://www.thecocktaildb.com/images/ingredients/$ingredient-small.png",
                    contentDescription = ingredient,
                    loading = placeholder {
                        LoadingIndicator(
                            modifier = Modifier
                                .padding(50.dp)
                                .fillMaxSize()) },
                    modifier = modifier
                        .padding(4.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit,
                )
                Text(text = "$measure$ingredient")
            }
        }

        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = drink.strInstructions ?: "",
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    LoadingIndicator(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ContentError(modifier: Modifier = Modifier) {

}