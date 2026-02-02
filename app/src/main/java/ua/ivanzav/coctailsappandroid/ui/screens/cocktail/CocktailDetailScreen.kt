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
import androidx.compose.material3.LoadingIndicator
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import ua.ivanzav.coctailsappandroid.data.model.CocktailDataJson
import ua.ivanzav.coctailsappandroid.data.model.CocktailsModelJson

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.CocktailDetailScreen(
    /*cocktailModel: CocktailDataJson,*/
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

    val informationText = when (cocktailDetailUiState) {
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
            .background(Color(0xFF252525)),
    ) {
        Column {
            Surface(
                modifier = modifier
                    .padding(16.dp)
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

            Spacer(Modifier.padding(16.dp))

            Text(
                text = labelText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "text/$labelText"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 300)
                        }
                    )
            )

            Text(
                text = drink?.strInstructions ?: "Loading",
                color = Color.White,
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}