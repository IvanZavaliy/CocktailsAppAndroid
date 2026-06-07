# CocktailAppAndroid — Developer Documentation

## 1. Project Overview

**Cocktails App** is a native Android application designed for cocktail enthusiasts and bartenders. It allows users to search for cocktail recipes, filter them by category (alcoholic/non-alcoholic) and by ingredient, view detailed ingredients and instructions, and manage a personalized list of favorite drinks synced to their account via the cloud.

The application leverages a custom backend API (hosted on Railway) for cocktail data retrieval, **Firebase Authentication** for secure Google Sign-In functionality, and **Firebase Cloud Firestore** for storing user-specific favorite cocktails. It is built using modern Android development standards, including **Kotlin** and **Jetpack Compose**.

## 2. Key Features

* **Browse Cocktails** — View lists of alcoholic and non-alcoholic cocktails with bottom navigation tabs.
* **Search** — Full-text search across the cocktail database with results filtered by current category.
* **Filter by Ingredient** — Use the side drawer to filter cocktails by a specific ingredient.
* **Cocktail Details** — View full recipe details: image, category, glass type, ingredients with measures, and step-by-step instructions.
* **Ingredient Details** — Tap any ingredient in a recipe to view its detailed description and image.
* **Google Sign-In** — Authenticate with a Google account via Firebase Authentication.
* **Favorites (Firebase Firestore)** — Add or remove cocktails from a personal favorites list that is stored in the user's Firebase account and synced across devices.
* **Profile Page** — View user profile info (avatar, name) and the full list of favorited cocktails.
* **Shared Element Transitions** — Smooth, animated transitions between list items and detail screens.
* **Material Design 3** — Modern UI with dynamic theming and Material You support.

## 3. Technology Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose (Material Design 3) |
| Architecture | MVVM (Model-View-ViewModel) |
| Networking | Retrofit 2, OkHttp, Kotlinx Serialization |
| Asynchronous Programming | Kotlin Coroutines & Flow |
| Authentication | Firebase Auth (Google Sign-In) |
| Cloud Database | Firebase Cloud Firestore |
| Image Loading | Glide (Compose), Coil (Compose) |
| Dependency Injection | Manual Container Pattern (`CocktailAppContainer`) |
| Navigation | Jetpack Navigation Compose |
| Animations | Shared Element Transitions, AnimatedVisibility |

## 4. Project Structure

The project follows a clean separation of concerns. Below is the complete package structure:

```text
ua.ivanzav.coctailsappandroid
├── CocktailsApplication.kt              # Application class, initializes DI container
├── MainActivity.kt                       # Single Activity entry point
├── data                                  # Data Layer
│   ├── api                               # DI Container & Retrofit configuration
│   │   └── CocktailAppContainer.kt       # Provides repositories & API services
│   ├── model                             # Data classes (DTOs)
│   │   ├── CocktailDetailDataJson.kt     # Detailed cocktail model (full recipe)
│   │   ├── CocktailsDataJson.kt          # Cocktail list item model (id, name, image)
│   │   └── IngredientJson.kt             # Ingredient model
│   └── repository                        # Repository implementations
│       ├── CocktailsAppRepository.kt     # Cocktail API data repository
│       └── FavoriteRepository.kt         # Firebase Firestore favorites repository
├── di                                    # API Service Interfaces (Retrofit)
│   ├── AlcoholCocktailApiService.kt      # GET alcoholic cocktails
│   ├── CocktailDetailApiService.kt       # GET cocktail details by ID
│   ├── CocktailSearchApiService.kt       # GET search cocktails by name
│   ├── IngredientsFilterApiService.kt    # GET ingredients list / filter by ingredient
│   └── NonAlcoholApiService.kt           # GET non-alcoholic cocktails
├── presentation                          # Presentation Logic
│   └── sign_in                           # Google Sign-In Logic
│       ├── GoogleAuthUiClient.kt         # Google Sign-In client wrapper
│       ├── SignInResult.kt               # Sign-in result & UserData models
│       └── SignInState.kt                # Sign-in UI state
└── ui                                    # User Interface (Jetpack Compose)
    ├── RootApplication.kt                # Root composable, Scaffold, NavHost
    ├── components                        # Reusable UI components
    │   ├── CocktailCard.kt               # Cocktail card with image & title
    │   └── drawer                        # Navigation Drawer
    │       └── DrawerContent.kt          # Ingredient filter drawer content
    ├── navigation                        # Navigation & Shared ViewModels
    │   ├── AppNavigation.kt              # NavHost setup & route definitions
    │   ├── BottomNavItems.kt             # Bottom navigation tab enum
    │   ├── ScreenPageNav.kt              # UI state sealed interface
    │   └── SearchViewModel.kt            # Search logic & favorite IDs tracking
    ├── screens                           # Application Screens
    │   ├── BaseScreens.kt                # Loading & Error composables
    │   ├── account                       # Account / Profile Feature
    │   │   ├── AccountNavController.kt   # Account page orchestrator
    │   │   ├── FavoritesViewModel.kt     # Favorites list ViewModel
    │   │   ├── ProfileScreen.kt          # Profile UI with favorites grid
    │   │   ├── SignInScreen.kt           # Sign-in screen UI
    │   │   └── SignInViewModel.kt        # Sign-in state ViewModel
    │   ├── cocktail                      # Cocktail Detail Feature
    │   │   ├── CocktailDetailScreen.kt   # Detail screen with favorite toggle
    │   │   ├── CocktailDetailUiState.kt  # Detail UI state sealed interface
    │   │   └── CocktailDetailViewModel.kt# Detail ViewModel with favorite logic
    │   ├── cocktailslist                 # Cocktails List Feature
    │   │   ├── CocktailsListScreen.kt    # Cocktail grid/list screen
    │   │   └── CocktailsListViewModel.kt # List ViewModel (fetch by category)
    │   └── ingredient                    # Ingredient Detail Feature
    │       ├── IngredientDetailScreen.kt  # Ingredient detail screen
    │       └── IngredientDetailViewModel.kt # Ingredient ViewModel
    └── theme                             # App Theme
        ├── Color.kt                      # Color palette definitions
        ├── Theme.kt                      # Material 3 theme configuration
        └── Type.kt                       # Typography definitions
```

## 5. Favorites Feature — Firebase Firestore

### Overview

The favorites feature allows authenticated users to save cocktails to a personal favorites list. The data is stored in **Firebase Cloud Firestore** and synced across all devices where the user is signed in.

### Data Model (Firestore)

```text
Firestore Database
└── users (collection)
    └── {userId} (document)
        └── drinks (array of maps)
            ├── { idDrink: "11007", strDrink: "Margarita", strDrinkThumb: "https://..." }
            ├── { idDrink: "11000", strDrink: "Mojito", strDrinkThumb: "https://..." }
            └── ...
```

Each user document contains a `drinks` array field. Each element is a map with keys: `idDrink`, `strDrink`, `strDrinkThumb`.

### Architecture

| Component | File | Responsibility |
|---|---|---|
| Repository Interface | `FavoriteRepository.kt` | Defines CRUD operations: `addFavorite`, `removeFavorite`, `getFavorites`, `isFavorite` |
| Repository Implementation | `FirestoreFavoriteRepository` | Implements the interface using Firebase Firestore SDK (`FieldValue.arrayUnion` / `arrayRemove`) |
| DI Container | `CocktailAppContainer.kt` | Provides a singleton `FavoriteRepository` instance backed by `FirebaseFirestore` |
| Favorites ViewModel | `FavoritesViewModel.kt` | Loads the user's favorites list for display on the Profile screen |
| Detail ViewModel | `CocktailDetailViewModel.kt` | Checks if a cocktail is favorited and handles the toggle action |
| Search ViewModel | `SearchViewModel.kt` | Tracks `favoriteIds` set to display favorite indicators on list items |
| Profile Screen | `ProfileScreen.kt` | Displays the user's favorites in a grid, with navigation to cocktail details |
| Detail Screen | `CocktailDetailScreen.kt` | Shows a Favorite FAB button (filled/outlined heart icon) with toggle functionality |

### User Flow

1. **Sign In** → User authenticates with Google via Firebase Auth.
2. **Browse / Search** → Cocktail cards display a filled heart icon if the cocktail is in the user's favorites.
3. **Add to Favorites** → On the cocktail detail screen, tap the heart FAB → cocktail is added to Firestore via `FieldValue.arrayUnion`.
4. **Remove from Favorites** → Tap the heart FAB again → cocktail is removed via `FieldValue.arrayRemove`.
5. **View Favorites** → Navigate to the Account tab → Profile screen shows user info and a grid of all favorited cocktails.
6. **Cross-device Sync** → Since data is in Firestore, favorites are available on any device after signing in.

## 6. Quick Start

### Prerequisites

* Android Studio Ladybug or newer.
* JDK 17 or newer.
* Android SDK API Level 36.

### Installation Steps

**1. Clone the Repository**

```bash
git clone https://github.com/IvanZavaliy/CocktailsAppAndroid.git
cd CocktailsAppAndroid
```

**2. Configure Firebase**

This project uses Firebase Authentication and Cloud Firestore. You must configure your own Firebase project:

* Go to the [Firebase Console](https://console.firebase.google.com/).
* Create a new project (or use an existing one).
* Enable **Google Sign-In** under Authentication → Sign-in method.
* Enable **Cloud Firestore** under Firestore Database (start in test mode or configure security rules).
* Download `google-services.json` and place it in: `CocktailsAppAndroid/app/google-services.json`.

**3. Build and Run**

* Open the project in Android Studio.
* Allow Gradle to sync dependencies.
* Select the `app` configuration and click **Run**.

**4. Emulator / Device Setup**

* Ensure your emulator or physical device is running **Android 8.0 (API 26)** or higher.
* Ensure the device has **Google Play Services** installed (required for Firebase Auth and Firestore).

## 7. Gradle Dependencies

Key dependencies from `app/build.gradle.kts`:

```kotlin
plugins {
    id("com.google.gms.google-services")               // Firebase plugin
    id("org.jetbrains.kotlin.plugin.serialization")     // Kotlin Serialization
}

dependencies {
    // Jetpack Compose (BOM)
    implementation(platform("androidx.compose:compose-bom:..."))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.7")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

    // Networking (Retrofit + Kotlinx Serialization)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Image Loading
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    implementation("com.google.android.gms:play-services-auth:21.5.0")
}
```

## 8. Architecture

The application is built upon the **MVVM (Model-View-ViewModel)** architecture:

* **Model (Data Layer):** Located in `data/`. Contains data classes (DTOs) representing API responses (e.g., `CocktailDetailDataJson`, `CocktailsDataJson`). The `CocktailsAppRepository` abstracts network data sources, and `FavoriteRepository` abstracts Firebase Firestore operations.
* **ViewModel (Presentation Layer):** Manages UI state and business logic. Each feature has its own ViewModel (`CocktailsListViewModel`, `CocktailDetailViewModel`, `FavoritesViewModel`, `SearchViewModel`, `SignInViewModel`). ViewModels communicate with repositories and expose state via `mutableStateOf` for Compose observation.
* **View (UI Layer):** Built entirely with **Jetpack Compose**. The `screens` package contains Composable functions that observe ViewModel state and render the UI. Shared element transitions provide smooth animations between list and detail views.

```text
┌─────────────────────────────────────────────────────────────┐
│                     UI Layer (Compose)                       │
│  Screens: CocktailsList, CocktailDetail, Profile, SignIn    │
│  Components: CocktailCard, DrawerContent                    │
│  Navigation: AppNavigation, BottomNavItems                  │
└───────────────────────────┬─────────────────────────────────┘
                            │ observes state
┌───────────────────────────▼─────────────────────────────────┐
│                  ViewModel Layer                             │
│  CocktailsListVM, CocktailDetailVM, FavoritesVM,           │
│  SearchVM, SignInVM                                          │
└───────────────────────────┬─────────────────────────────────┘
                            │ calls repositories
┌───────────────────────────▼─────────────────────────────────┐
│                    Data Layer                                │
│  CocktailsAppRepository ←→ Retrofit API Services            │
│  FavoriteRepository      ←→ Firebase Cloud Firestore        │
│  GoogleAuthUiClient      ←→ Firebase Auth                   │
└─────────────────────────────────────────────────────────────┘
```

## 9. API Documentation

The app retrieves cocktail data via a custom backend API hosted on Railway:

Base URL: `https://cocktailsappapi-production.up.railway.app/`

**Key Endpoints used:**

| Endpoint | Description |
|---|---|
| `search.php?s={name}` | Search cocktail by name |
| `filter.php?a=Alcoholic` | Filter by alcoholic cocktails |
| `filter.php?a=Non_Alcoholic` | Filter by non-alcoholic cocktails |
| `lookup.php?i={id}` | Lookup full cocktail details by ID |
| `list.php?i=list` | List all available ingredients |
| `filter.php?i={ingredient}` | Filter cocktails by ingredient |

## 10. Firebase Security Rules (Recommended)

For production, configure Firestore security rules to restrict access:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

This ensures each user can only read and modify their own favorites document.
