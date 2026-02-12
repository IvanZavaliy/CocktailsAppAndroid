package ua.ivanzav.coctailsappandroid.ui

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.ivanzav.coctailsappandroid.presentation.sign_in.GoogleAuthUiClient
import ua.ivanzav.coctailsappandroid.ui.navigation.NavigationBarApp
import ua.ivanzav.coctailsappandroid.ui.screens.cocktail.CocktailDetailScreen
import ua.ivanzav.coctailsappandroid.ui.screens.ingredient.IngredientDetailScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RootApplication() {
    val navController = rememberNavController()

    fun encodeUrl(url: String): String {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    }

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                NavigationBarApp(
                    animatedVisibilityScope = this,
                    onNavigateToDetail = { url, name, id ->
                        navController.navigate("detail?image=$url&text=$name&id=$id")
                    }
                )
            }

            composable(
                route = "detail?image={image}&text={text}&id={id}",
                arguments = listOf(
                    navArgument("image") { type = NavType.StringType },
                    navArgument("text") { type = NavType.StringType },
                    navArgument("id") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val image = backStackEntry.arguments?.getString("image") ?: ""
                val text = backStackEntry.arguments?.getString("text") ?: ""
                val drinkId = backStackEntry.arguments?.getString("id") ?: ""

                CocktailDetailScreen(
                    imageUrl = image,
                    labelText = text,
                    drinkId = drinkId,
                    animatedVisibilityScope = this,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onIngredientClick = { ingredientName, ingredientImageUrl ->
                        val encodedUrl = encodeUrl(ingredientImageUrl)
                        navController.navigate("ingredient?name=$ingredientName&image=$encodedUrl")
                    }
                )
            }

            composable(
                route = "ingredient?name={name}&image={image}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("image") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val image = backStackEntry.arguments?.getString("image") ?: ""

                IngredientDetailScreen(
                    imageUrl = image,
                    ingredientName = name,
                    animatedVisibilityScope = this,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCocktailClick = { url, cocktailName, id ->
                        val encodedUrl = encodeUrl(url)
                        navController.navigate("detail?image=$encodedUrl&text=$cocktailName&id=$id")
                    }
                )
            }
        }
    }
}