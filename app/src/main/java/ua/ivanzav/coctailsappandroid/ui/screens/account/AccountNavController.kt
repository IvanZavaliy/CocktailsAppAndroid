package ua.ivanzav.coctailsappandroid.ui.screens.account

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.ivanzav.coctailsappandroid.presentation.sign_in.UserData

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AccountPage(
    userData: UserData?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCocktailClick: (String, String, String) -> Unit,
    onSignOut: () -> Unit
) {
    val favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)

    LaunchedEffect(userData) {
        if (userData?.userId != null) {
            favoritesViewModel.getFavorites(userData.userId)
        }
    }

    if (userData != null) {
        ProfileScreen(
            userData = userData,
            favorites = favoritesViewModel.favorites,
            animatedVisibilityScope = animatedVisibilityScope,
            onCocktailClick = onCocktailClick,
            onSignOut = onSignOut
        )
    } else {
        // Fallback, якщо даних немає
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading profile...")
        }
    }
}