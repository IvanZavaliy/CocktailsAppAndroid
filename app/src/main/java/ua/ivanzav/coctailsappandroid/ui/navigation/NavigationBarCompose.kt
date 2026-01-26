package ua.ivanzav.coctailsappandroid.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ua.ivanzav.coctailsappandroid.ui.screens.BaseScreen
import ua.ivanzav.coctailsappandroid.ui.screens.alcohol.AlcoholViewModel
import ua.ivanzav.coctailsappandroid.ui.screens.nonalcohol.NonAlcoholViewModel

@Preview(showBackground = true)
@Composable
fun NavigationBarApp(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = {BottomNavItems.entries.size})
    val startDestination = BottomNavItems.ALCOHOL
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                BottomNavItems.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            selectedDestination = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppPagerHost(pagerState, modifier = Modifier.padding(contentPadding))
    }
}

@Composable
fun AppPagerHost(
    pagerState: PagerState,
    modifier: Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { pageIndex ->
        val isVisible = pagerState.currentPage == pageIndex

        when (BottomNavItems.entries[pageIndex]) {
            BottomNavItems.ALCOHOL ->
            {
                val alcoholViewModel: AlcoholViewModel = viewModel(
                    factory = AlcoholViewModel.Factory
                )

                LaunchedEffect(isVisible) {
                    if (isVisible) {
                        alcoholViewModel.getAlcoholCocktailModels()
                    }
                }

                BaseScreen(
                    cocktailsAppUiState = alcoholViewModel.alcoholUiState,
                    retryAction = alcoholViewModel::getAlcoholCocktailModels
                )
            }
            BottomNavItems.NONALCOHOL ->
            {
                val nonAlcoholViewModel: NonAlcoholViewModel = viewModel(
                    factory = NonAlcoholViewModel.Factory
                )

                LaunchedEffect(isVisible) {
                    if (isVisible) {
                        nonAlcoholViewModel.getNonAlcoholicCocktailModels()
                    }
                }

                BaseScreen(
                    cocktailsAppUiState =  nonAlcoholViewModel.nonAlcoholUiState,
                    retryAction = nonAlcoholViewModel::getNonAlcoholicCocktailModels
                )
            }
        }
    }
}