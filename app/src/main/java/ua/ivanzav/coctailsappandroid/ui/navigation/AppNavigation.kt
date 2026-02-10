package ua.ivanzav.coctailsappandroid.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ua.ivanzav.coctailsappandroid.CocktailsApplication
import ua.ivanzav.coctailsappandroid.ui.components.drawer.DrawerContent
import ua.ivanzav.coctailsappandroid.ui.screens.BaseScreen
import ua.ivanzav.coctailsappandroid.ui.screens.cocktailslist.CocktailsListViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.NavigationBarApp(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetail: (String, String, String) -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as CocktailsApplication
    val repository = application.container.cocktailsAppRepository

    val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    var currentIngredient by rememberSaveable { mutableStateOf<String?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val pagerState = rememberPagerState(pageCount = {BottomNavItems.entries.size})
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val closeSearch = {
        isSearchActive = false
        searchViewModel.resetSearchState()
    }

    BackHandler(enabled = isSearchActive) {
        closeSearch()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    repository = repository,
                    selectedIngredient = currentIngredient,
                    onIngredientSelected = { ingredient ->
                        currentIngredient = ingredient
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                ApplicationTopBar(
                    scrollBehavior = scrollBehavior,
                    isSearchActive = isSearchActive,
                    onSearchActiveChange = { isActive ->
                        if (!isActive) {
                            closeSearch()
                        } else {
                            isSearchActive = true
                        }
                    },
                    searchViewModel = searchViewModel,
                    onFilterClick = {
                        coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open()
                                else close()
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    BottomNavItems.entries.forEachIndexed { index, destination ->
                        val isSelected = pagerState.currentPage == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }

                                searchViewModel.updateCategory(destination)
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

            AppPagerHost(
                pagerState = pagerState,
                modifier = Modifier.padding(contentPadding),
                animatedVisibilityScope = animatedVisibilityScope,
                onNavigateToDetail = onNavigateToDetail,
                isSearchActive = isSearchActive,
                searchViewModel = searchViewModel,
                selectedIngredient = currentIngredient,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AppPagerHost(
    pagerState: PagerState,
    modifier: Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetail: (String, String, String) -> Unit,
    isSearchActive: Boolean,
    searchViewModel: SearchViewModel,
    selectedIngredient: String?
) {
    LaunchedEffect(pagerState.currentPage) {
        val currentCategory = BottomNavItems.entries[pagerState.currentPage]
        searchViewModel.updateCategory(currentCategory)
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { pageIndex ->
        val currentTab = BottomNavItems.entries[pageIndex]

        if (isSearchActive && searchViewModel.isSearchExecuted) {
            BaseScreen(
                cocktailsAppUiState = searchViewModel.searchUiState,
                retryAction = searchViewModel::performSearch,
                animatedVisibilityScope = animatedVisibilityScope,
                onItemClick = onNavigateToDetail,
                modifier = Modifier.fillMaxSize()
            )

        } else {
            when (currentTab) {
                BottomNavItems.ALCOHOL ->
                {
                    val alcoViewModel: CocktailsListViewModel = viewModel(
                        key = "AlcoholViewModel",
                        factory = CocktailsListViewModel.provideFactory(CocktailsPage.ALCOHOL)
                    )

                    LaunchedEffect(selectedIngredient) {
                        alcoViewModel.fetchCocktails(selectedIngredient)
                    }

                    BaseScreen(
                        cocktailsAppUiState = alcoViewModel.cocktailsUiState,
                        retryAction = alcoViewModel::getAlcoholCocktailModels,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onItemClick = onNavigateToDetail
                    )
                }
                BottomNavItems.NONALCOHOL ->
                {
                    val nonAlcoViewModel: CocktailsListViewModel = viewModel(
                        key = "NonAlcoholViewModel",
                        factory = CocktailsListViewModel.provideFactory(CocktailsPage.NONALCOHOL)
                    )

                    LaunchedEffect(selectedIngredient) {
                        nonAlcoViewModel.fetchCocktails(selectedIngredient)
                    }

                    BaseScreen(
                        cocktailsAppUiState = nonAlcoViewModel.cocktailsUiState,
                        retryAction = nonAlcoViewModel::getNonAlcoholCocktailModels,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onItemClick = onNavigateToDetail
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ApplicationTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isSearchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit,
    searchViewModel: SearchViewModel,
    onFilterClick: () -> Unit
) {
    val query = searchViewModel.searchQuery

    if(isSearchActive) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                SearchBar(
                    query = query,
                    onQueryChange = searchViewModel::updateQuery,
                    onSearch = searchViewModel::performSearch
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        onSearchActiveChange(false)
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    } else {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = "Cocktails App",
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            navigationIcon = {
                IconButton(onClick = onFilterClick) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = "Filter Ingredients"
                    )
                }
            },

            actions = {
                IconButton(onClick = {
                    onSearchActiveChange(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Button"
                    )
                }
            }
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Search cocktails...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}