package ua.ivanzav.coctailsappandroid.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ua.ivanzav.coctailsappandroid.data.model.CocktailModelJson

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CocktailCard(cocktailModel: CocktailModelJson, modifier: Modifier = Modifier) {
    val cardImageViewModel: CocktailCardViewModel = viewModel(
        factory = CocktailCardViewModel.provideFactory(cocktailModel.image)
    )

    OutlinedCard(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF252525)
        ),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column{
            ImageContent(cardImageViewModel.cardUiState, cocktailModel.image)

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
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ImageContent(
    cardImageUiState: CardImageUiState,
    imageUrl: String
) {
    when (cardImageUiState) {
        is CardImageUiState.Loading -> CardLoadingIndicator()
        is CardImageUiState.Success -> CardImage(imageUrl, modifier = Modifier.fillMaxSize(1f))
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CardLoadingIndicator(modifier: Modifier = Modifier) {
    BoxWithConstraints {
        val boxWidth = maxWidth

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(boxWidth),
            contentAlignment = Alignment.Center
        ) {
            LoadingIndicator(modifier = Modifier.size(100.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardImage(imageUrl: String, modifier: Modifier = Modifier) {
    BoxWithConstraints {
        val boxWidth = maxWidth

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(boxWidth)
        ) {
            GlideImage(
                model = imageUrl,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CocktailCardPreview(){
    Box() {
        CocktailCard(CocktailModelJson("Cocktail", "https://www.thecocktaildb.com/images/media/drink/xxyywq1454511117.jpg", "123"))
    }
}