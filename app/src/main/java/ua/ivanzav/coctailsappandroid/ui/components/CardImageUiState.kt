package ua.ivanzav.coctailsappandroid.ui.components

sealed interface CardImageUiState {
    data class Success (val imageUrl: String): CardImageUiState
    object Loading: CardImageUiState
}