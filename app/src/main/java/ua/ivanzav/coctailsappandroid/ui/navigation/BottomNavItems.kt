package ua.ivanzav.coctailsappandroid.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    ALCOHOL("alcohol", "Alcohol", Icons.Default.Person, "Alcohol"),
    NONALCOHOL("non-alcohol", "Non-Alcohol", Icons.Default.Face, "Non-Alcohol")
}