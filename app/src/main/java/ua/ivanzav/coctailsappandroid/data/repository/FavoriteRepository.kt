package ua.ivanzav.coctailsappandroid.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ua.ivanzav.coctailsappandroid.data.model.CocktailsDataJson

interface FavoriteRepository {
    suspend fun addFavorite(userId: String, cocktail: CocktailsDataJson)
    suspend fun removeFavorite(userId: String, drinkId: String)
    suspend fun getFavorites(userId: String): List<CocktailsDataJson>
    suspend fun isFavorite(userId: String, drinkId: String): Boolean
}

class FirestoreFavoriteRepository(
    private val firestore: FirebaseFirestore
) : FavoriteRepository {

    private val usersCollection = firestore.collection("users")

    override suspend fun addFavorite(userId: String, cocktail: CocktailsDataJson) {
        val cocktailMap = mapOf(
            "idDrink" to cocktail.id,
            "strDrink" to cocktail.name,
            "strDrinkThumb" to cocktail.image
        )
        try {
            usersCollection.document(userId)
                .update("drinks", FieldValue.arrayUnion(cocktailMap))
                .await()
        } catch (e: Exception) {
            // If document doesn't exist, create it
            usersCollection.document(userId)
                .set(mapOf("drinks" to listOf(cocktailMap)))
                .await()
        }
    }

    override suspend fun removeFavorite(userId: String, drinkId: String) {
        val favorites = getFavorites(userId)
        val cocktailToRemove = favorites.find { it.id == drinkId }
        
        if (cocktailToRemove != null) {
            val cocktailMap = mapOf(
                "idDrink" to cocktailToRemove.id,
                "strDrink" to cocktailToRemove.name,
                "strDrinkThumb" to cocktailToRemove.image
            )
            usersCollection.document(userId)
                .update("drinks", FieldValue.arrayRemove(cocktailMap))
                .await()
        }
    }

    override suspend fun getFavorites(userId: String): List<CocktailsDataJson> {
        return try {
            val snapshot = usersCollection.document(userId).get().await()
            val drinks = snapshot.get("drinks") as? List<Map<String, String>> ?: emptyList()
            drinks.map {
                CocktailsDataJson(
                    name = it["strDrink"] ?: "",
                    image = it["strDrinkThumb"] ?: "",
                    id = it["idDrink"] ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun isFavorite(userId: String, drinkId: String): Boolean {
        val favorites = getFavorites(userId)
        return favorites.any { it.id == drinkId }
    }
}
