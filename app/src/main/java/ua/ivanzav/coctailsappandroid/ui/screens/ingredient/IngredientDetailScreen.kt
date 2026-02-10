package ua.ivanzav.coctailsappandroid.ui.screens.ingredient

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.IngredientDetailScreen(
    imageUrl: String,
    ingredientName: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackClick: () -> Unit,
    onCocktailClick: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {

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
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = ingredientName,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = modifier
                            .padding(top = 16.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "text/$ingredientName"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 300)
                                }
                            )
                    )

                    //тут буде when() з визовом функції контенту екрану
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