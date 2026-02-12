package ua.ivanzav.coctailsappandroid.ui.screens.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.ivanzav.coctailsappandroid.presentation.sign_in.UserData

@Composable
fun AccountPage(
    userData: UserData?,
    onSignOut: () -> Unit
) {

    if (userData != null) {
        ProfileScreen(
            userData = userData,
            onSignOut = onSignOut
        )
    } else {
        // Fallback, якщо даних немає
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading profile...")
        }
    }
}