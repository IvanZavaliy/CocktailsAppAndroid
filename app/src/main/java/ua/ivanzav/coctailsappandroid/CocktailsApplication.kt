package ua.ivanzav.coctailsappandroid

import android.app.Application
import ua.ivanzav.coctailsappandroid.data.api.CocktailAppContainer
import ua.ivanzav.coctailsappandroid.data.api.DefaultCocktailAppContainer

class CocktailsApplication : Application() {
    lateinit var container: CocktailAppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultCocktailAppContainer()
    }
}