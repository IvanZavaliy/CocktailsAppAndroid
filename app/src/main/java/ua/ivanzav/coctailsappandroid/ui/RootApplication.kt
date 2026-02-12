package ua.ivanzav.coctailsappandroid.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import ua.ivanzav.coctailsappandroid.presentation.sign_in.GoogleAuthUiClient
import ua.ivanzav.coctailsappandroid.ui.navigation.NavigationBarApp
import ua.ivanzav.coctailsappandroid.ui.screens.account.SignInScreen
import ua.ivanzav.coctailsappandroid.ui.screens.account.SignInViewModel
import ua.ivanzav.coctailsappandroid.ui.screens.cocktail.CocktailDetailScreen
import ua.ivanzav.coctailsappandroid.ui.screens.ingredient.IngredientDetailScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RootApplication() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val applicationContext = LocalContext.current

    val googleAuthUiClient = remember {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    fun encodeUrl(url: String): String {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    }

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                LaunchedEffect(key1 = Unit) {
                    if(googleAuthUiClient.getSignedInUser() == null) {
                        navController.navigate("sign_in") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                }

                NavigationBarApp(
                    animatedVisibilityScope = this,
                    userData = googleAuthUiClient.getSignedInUser(),
                    onNavigateToDetail = { url, name, id ->
                        navController.navigate("detail?image=$url&text=$name&id=$id")
                    },
                    onSignOut = {
                        scope.launch {
                            googleAuthUiClient.signOut()
                            navController.navigate("sign_in") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
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

            composable(route = "sign_in") {
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    if(googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate("home")
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            scope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if(state.isSignInSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("home")
                        viewModel.resetState()
                    }
                }

                SignInScreen(
                    state = state,
                    onSignInClick = {
                        scope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }
        }
    }
}