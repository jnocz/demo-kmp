/*
 * SearchScreen.kt
 * 21.11.2023
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2023. All rights reserved.
 *
 */

package screen.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.app_offline
import bazarsearchmultiplatform.shared.generated.resources.generic_error_text
import bazarsearchmultiplatform.shared.generated.resources.no_records_found
import bazarsearchmultiplatform.shared.generated.resources.search
import bazarsearchmultiplatform.shared.generated.resources.searching
import bazarsearchmultiplatform.shared.generated.resources.start_searching
import engine.domain.SearchResult
import engine.domain.SearchedPage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import screen.search.list.SearchedItemView
import ui.component.CustomTopAppBar
import ui.component.GifImage
import ui.component.SearchTextField
import ui.component.list.showLoadingStates
import ui.component.screen.BlankView
import ui.theme.BazarSearchTheme
import ui.theme.color.AppColorPalette
import utils.OpenBrowserUrl
import utils.ResultState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    initPhrase: String? = null
) {

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val initPhraseRemembered = rememberSaveable {
        mutableStateOf(initPhrase)
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            Column {
                CustomTopAppBar(
                    title = stringResource(Res.string.search),
                    navigationIcon = {},
                    scrollBehavior = scrollBehavior
                )
                SearchTextField(initPhraseRemembered.value) {
                    viewModel.onSearchQueryChanged(it)
                }
            }

        }) {

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .wrapContentHeight(unbounded = false)
        ) {

            val state = rememberLazyListState()

            LaunchedEffect(Unit) {
                if (initPhraseRemembered.value?.isNotBlank() == true) {
                    viewModel.onSearchQueryChanged(initPhraseRemembered.value!!)
                }
            }

            val searchResults by rememberUpdatedState(viewModel.searchResults.collectAsLazyPagingItems())

            when (viewModel.uiState.loadingResult.state) {
                ResultState.INIT -> {
                    if (searchResults.itemCount == 0) {
                        BlankView(
                            Res.string.start_searching,
                            Res.getUri(DrawableConstants.SEARCH_INIT.value)
                        )
                    } else {
                        SearchedItemsList(
                            searchResults,
                            state,
                            { viewModel.toggleFavoriteItem(it) })
                    }
                }

                ResultState.BLANK -> SearchedItemsList(
                    searchResults,
                    state,
                    { viewModel.toggleFavoriteItem(it) })

                ResultState.ERROR -> MessageView(text = stringResource(Res.string.generic_error_text))
                ResultState.LOADING -> LoadingView(
                    viewModel.uiState.loaderResourceUrl,
                    viewModel.uiState.currentSearchedPage
                )

                ResultState.NETWORK_ERROR -> MessageView(text = stringResource(Res.string.app_offline))
                ResultState.SUCCESS -> SearchedItemsList(
                    searchResults,
                    state,
                    { viewModel.toggleFavoriteItem(it) })
            }
        }
    }
}

@Composable
fun LoadingView(imageUri: String, currentSearchedPage: SearchedPage?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                color = AppColorPalette.lightGray,
                fontSize = 17.sp,
                text = (stringResource(Res.string.searching) + " " + currentSearchedPage?.prettyDomainUrl)
            )
            GifImage(imageUri = imageUri)
        }
    }
}


@Composable
fun MessageView(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = text)
    }
}

val topPadding = 230.dp
val bottomPadding = 150.dp

@Composable
fun SearchedItemsList(
    searchResults: LazyPagingItems<SearchResult>,
    state: LazyListState,
    onFavoriteClickListener: (SearchResult) -> Unit
) {

    var openWebBrowser by remember { mutableStateOf(false) }
    var openWebBrowserUrl by remember { mutableStateOf("") }

    if (openWebBrowser) {
        if (openWebBrowserUrl.isNotBlank()) {
            OpenBrowserUrl(openWebBrowserUrl)
        }
        openWebBrowser = false
        openWebBrowserUrl = ""
    }

    if (searchResults.itemCount == 0) {
        BlankView(Res.string.no_records_found, Res.getUri(DrawableConstants.NO_SEARCH_ITEM_AVAILABLE.value))
    } else {
        LazyColumn(
            contentPadding = PaddingValues(top = topPadding, bottom = bottomPadding), state = state
        ) {
            items(count = searchResults.itemCount) { index ->
                val searchResult = searchResults[index]
                searchResult?.let {
                    SearchedItemView(searchResult,false,
                        {
                            openWebBrowser = true
                            openWebBrowserUrl = searchResult.link
                        },
                        { onFavoriteClickListener(it) })
                }
            }

            showLoadingStates(searchResults)
        }
    }
}


@Composable
@Preview
fun SearchTextFieldPreview() {
    BazarSearchTheme {
        SearchScreen(koinViewModel(), "auto")
    }
}


