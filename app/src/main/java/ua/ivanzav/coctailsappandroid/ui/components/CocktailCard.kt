package ua.ivanzav.coctailsappandroid.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataJson

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.CocktailCard(
    cocktailModel: CocktailsDataJson,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier) {

    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column{
            Surface(
                modifier = modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "image/${cocktailModel.image}"),
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
                        model = cocktailModel.image,
                        contentDescription = "",
                        loading = placeholder {
                            LoadingIndicator(
                                modifier = Modifier
                                    .padding(50.dp)
                                    .fillMaxSize()) },
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .background(color = Color(0xFF252525))
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Text(
                    text = cocktailModel.name,
                    minLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "text/${cocktailModel.name}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 300)
                            }
                        )
                )
            }
        }
    }
}