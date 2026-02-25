# CocktailAppAndroid — Developer Documentation

## 1. Project Overview

**Cocktails App** is a native Android application designed for cocktail enthusiasts and bartenders. It allows users to search for cocktail recipes, filter them by category (alcoholic/non-alcoholic), view detailed ingredients and instructions, and manage a personalized list of favorite drinks.

The application leverages the **TheCocktailDB API** for data retrieval and **Firebase Authentication** for secure Google Sign-In functionality. It is built using modern Android development standards, including **Kotlin** and **Jetpack Compose**.

## 2. Technology Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles
* **Networking:** Retrofit 2, OkHttp, Gson Converter
* **Asynchronous Programming:** Kotlin Coroutines & Flow
* **Authentication:** Firebase Auth (Google Sign-In)
* **Image Loading:** Coil / Glide
* **Dependency Injection:** Manual Container Pattern (`CocktailAppContainer`)
* **Local Storage:** Room Database (for caching and favorites)

## 3. Project Structure

The project follows a clean separation of concerns. Below is the package structure based on the source code:

```text
ua.ivanzav.coctailsappandroid
├── data                         # Data layer: API handling and Data Models
│   ├── api                      # API definitions and Container
│   │   └── CocktailAppContainer.kt
│   ├── model                    # Data classes (DTOs)
│   │   ├── CocktailDetailDataJson.kt
│   │   ├── CocktailsDataJson.kt
│   │   └── IngredientJson.kt
│   └── repository               # Repository implementation
│       └── CocktailsAppRepository.kt
├── di                           # Dependency Injection / Service Interfaces
│   ├── AlcoholCocktailApiService.kt
│   ├── CocktailDetailApiService.kt
│   ├── CocktailSearchApiService.kt
│   └── ...
├── presentation                 # UI Logic / Authentication
│   └── sign_in                  # Google Sign-In Logic
│       ├── GoogleAuthUiClient.kt
│       ├── SignInResult.kt
│       └── SignInState.kt
├── ui                           # User Interface (Jetpack Compose)
│   ├── components               # Reusable UI components
│   │   └── CocktailCard.kt
│   ├── navigation               # Navigation Graph and Routes
│   │   ├── AppNavigation.kt
│   │   └── BottomNavItems.kt
│   ├── screens                  # Main Application Screens
│   │   ├── account              # User Profile
│   │   ├── cocktail             # Detailed Recipe View
│   │   ├── cocktailslist        # Search Results / Main Feed
│   │   └── ingredient           # Ingredient Details
│   └── theme                    # App Theme (Color, Type, Shape)
└── RootApplication.kt           # Application Entry Point

```

## 4. Quick Start

### Prerequisites

* Android Studio Iguana or newer.
* JDK 17 or newer.
* Android SDK API Level 34 (UpsideDownCake).

### Installation Steps

**1. Clone the Repository**

```bash
git clone https://github.com/IvanZavaliy/CocktailsAppAndroid.git
cd CocktailsAppAndroid

```

**2. Configure API Keys and Secrets**

* **Firebase:** This project uses Firebase Authentication. You must place your own `google-services.json` file in the `app/` directory.
* Go to the Firebase Console.
* Create a project and enable "Google Sign-In".
* Download `google-services.json` and move it to: `CocktailsAppAndroid/app/google-services.json`.


* **TheCocktailDB:** The app uses the free tier of TheCocktailDB (no API key required for basic endpoints), but if you have a premium key, update it in `CocktailAppContainer.kt`.

**3. Build and Run**

* Open the project in Android Studio.
* Allow Gradle to sync dependencies.
* Select the `app` configuration and click **Run**.

**4. Emulator Setup**

* Ensure your emulator or physical device is running **Android 12 (API 31)** or higher to support dynamic colors and modern security features.
* Ensure the device has Google Play Services installed (required for Firebase Auth).

## 5. Development Setup (Gradle Dependencies)

Key dependencies utilized in `build.gradle.kts`:

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Networking (Retrofit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Image Loading (Coil)
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
}

```

## 6. Architecture

The application is built upon the **MVVM (Model-View-ViewModel)** architecture:

* **Model (Data Layer):** Located in `data/`. Contains `Data Classes` (e.g., `CocktailDetailDataJson`) representing the JSON response from the API. The `Repository` (`CocktailsAppRepository`) abstracts the data sources.
* **ViewModel (Presentation Layer):** Manages the UI state and business logic. It communicates with the Repository to fetch data and exposes it to the UI via `StateFlow`.
* **View (UI Layer):** Built entirely with **Jetpack Compose**. The `screens` package contains Composable functions that observe the ViewModel state and render the UI.

## 7. API Documentation

The app interacts with **TheCocktailDB**.
Base URL: `https://www.thecocktaildb.com/api/json/v1/1/`

**Key Endpoints used:**

* `search.php?s={name}` - Search cocktail by name.
* `filter.php?a=Alcoholic` - Filter by Alcoholic.
* `filter.php?a=Non_Alcoholic` - Filter by Non-Alcoholic.
* `lookup.php?i={id}` - Lookup full cocktail details by ID.
