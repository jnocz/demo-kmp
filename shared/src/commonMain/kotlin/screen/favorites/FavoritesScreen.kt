/*
 * FavoritesScreen.kt
 * 21.11.2023
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2023. All rights reserved.
 *
 */

package screen.favorites


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.favorites
import bazarsearchmultiplatform.shared.generated.resources.no_favorites_found
import engine.domain.SearchResult
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import screen.favorites.list.FavoriteItem
import screen.search.DrawableConstants
import screen.search.bottomPadding
import screen.search.list.SearchedItemView
import ui.component.CustomTopAppBar
import ui.component.list.showLoadingStates
import ui.component.screen.BlankView
import ui.theme.BazarSearchTheme
import utils.OpenBrowserUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            Column {
                CustomTopAppBar(
                    title = stringResource(Res.string.favorites),
                    navigationIcon = {},
                    scrollBehavior = scrollBehavior
                )
            }
        }) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .wrapContentHeight(unbounded = false)
        ) {

            val favoritesResults by rememberUpdatedState(viewModel.favoritesResults.collectAsLazyPagingItems())

            if (favoritesResults.itemCount == 0) {
                BlankView(
                    Res.string.no_favorites_found,
                    Res.getUri(DrawableConstants.FAVOURITES_LIKE_ANIMATION.value)
                )
            } else {
                FavoriteItemsList(
                    favoritesResults,
                    {  viewModel.removeFavoriteItemWithLink(it.link) }
                )
            }
        }
    }
}

@Composable
fun FavoriteItemsList(
    favoritesResults: LazyPagingItems<FavoriteItem>,
    onFavoriteClickListener: (FavoriteItem) -> Unit
) {

    val state = rememberLazyListState()

    var openWebBrowser by remember { mutableStateOf(false) }
    var openWebBrowserUrl by remember { mutableStateOf("") }

    if (openWebBrowser) {
        if (openWebBrowserUrl.isNotBlank()) {
            OpenBrowserUrl(openWebBrowserUrl)
        }
        openWebBrowser = false
        openWebBrowserUrl = ""
    }

    LazyColumn(
        contentPadding = PaddingValues(top = 160.dp, bottom = bottomPadding), state = state
    ) {
        items(count = favoritesResults.itemCount) { index ->

            val favoriteItem = favoritesResults[index]
            favoriteItem?.let {
                val searchResult = Json.decodeFromString<SearchResult>(it.json)
                SearchedItemView(
                    searchResult,
                    true,
                    {
                        openWebBrowser = true
                        openWebBrowserUrl = searchResult.link
                    },
                    { onFavoriteClickListener(favoriteItem) }
                )
            }
        }

        showLoadingStates(favoritesResults)
    }
}

@Composable
@Preview
fun FavoritesScreenPreview() {
    BazarSearchTheme {
        FavoritesScreen(koinViewModel())
    }
}