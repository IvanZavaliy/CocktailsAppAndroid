package ua.ivanzav.coctailsappandroid.ui.screens.cocktail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import ua.ivanzav.coctailsappandroid.data.model.CocktailDataJson

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SharedTransitionScope.CocktailDetailScreen(
    /*cocktailModel: CocktailDataJson,*/
    imageUrl: String,
    labelText: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {

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
                            tween(durationMillis = 500)
                        }
                    ),
                shape = RoundedCornerShape(30.dp)
            ) {
                BoxWithConstraints {
                    val boxWidth = maxWidth

                    Box(
                        modifier = modifier
                            .fillMaxWidth(1f)
                            .height(boxWidth),
                        contentAlignment = Alignment.Center
                    ) {
                        GlideImage(
                            model = imageUrl,
                            contentDescription = "",
                            loading = placeholder {
                                LoadingIndicator(
                                    modifier = modifier
                                        .padding(50.dp)
                                        .fillMaxSize()) },
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .background(color = Color(0xFF252525))
                        )
                    }
                }
            }
            Spacer(Modifier.padding(16.dp))
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "text/$labelText"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 500)
                        }
                    )
            )
        }
    }
}