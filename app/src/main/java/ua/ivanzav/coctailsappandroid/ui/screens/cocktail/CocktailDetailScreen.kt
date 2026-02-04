package ua.ivanzav.coctailsappandroid.ui.screens.cocktail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ua.ivanzav.coctailsappandroid.data.model.CocktailDataJson
import ua.ivanzav.coctailsappandroid.data.model.CocktailsModelJson

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.CocktailDetailScreen(
    imageUrl: String,
    labelText: String,
    drinkId: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val cocktailDetailViewModel: CocktailDetailViewModel = viewModel(
        factory = CocktailDetailViewModel.Factory
    )
    LaunchedEffect(drinkId) {
        cocktailDetailViewModel.getCocktailDetailModel(drinkId)
    }

    val cocktailDetailUiState = cocktailDetailViewModel.cocktailDetailUiState
    val drink: CocktailDataJson?

    when (cocktailDetailUiState) {
        is CocktailDetailUiState.Loading -> {
            drink = null
        }
        is CocktailDetailUiState.Success -> {
            drink = cocktailDetailUiState.cocktailModelJson
        }
        is CocktailDetailUiState.Error -> {
            drink =  null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
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
                            .background(color = Color(0xFF252525))
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
                    MainDetailContent(drink, modifier)
                }
            }

        }
    }
}

@Composable
fun MainDetailContent(drink: CocktailDataJson?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${drink?.strCategory} | ${drink?.strGlass}" ?: "Loading",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .padding(4.dp)
        )
        Text(
            text = drink?.strInstructions ?: "Loading",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )
    }
}