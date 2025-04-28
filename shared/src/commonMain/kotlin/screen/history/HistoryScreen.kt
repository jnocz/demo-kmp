/*
 * HistoryScreen.kt
 * 21.11.2023
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2023. All rights reserved.
 *
 */

package screen.history


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.history
import bazarsearchmultiplatform.shared.generated.resources.no_history_found
import ui.component.HistoryItemView
import ui.component.list.showLoadingStates
import ui.component.screen.BlankView
import screen.history.list.HistoryItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import screen.search.DrawableConstants
import screen.search.bottomPadding
import ui.component.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = koinViewModel(),
    onNavigateToSearchScreen: (String?) -> Unit
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
                    title = stringResource(Res.string.history),
                    navigationIcon = {},
                    scrollBehavior = scrollBehavior
                )
            }

        }) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .wrapContentHeight(unbounded = false)
        ) {

            val historyResults by rememberUpdatedState(viewModel.historyResults.collectAsLazyPagingItems())

            val state = rememberLazyListState()

            if (historyResults.itemCount == 0) {
                BlankView(Res.string.no_history_found, Res.getUri(DrawableConstants.SEARCH_HISTORY.value))
            } else {
                HistoryItemsList(historyResults, state, onNavigateToSearchScreen)
            }
        }
    }
}

@Composable
fun HistoryItemsList(
    historyResults: LazyPagingItems<HistoryItem>,
    state: LazyListState,
    onHistoryItemClick: (String?) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 160.dp, bottom = bottomPadding), state = state
    ) {
        items(count = historyResults.itemCount) { index ->

            val historyItem = historyResults[index]
            historyItem?.let {
                HistoryItemView(
                    historyItem
                ) {
                    onHistoryItemClick(it)
                }
            }
            HorizontalDivider(thickness = Dp.Hairline)
        }

        showLoadingStates(historyResults)
    }
}