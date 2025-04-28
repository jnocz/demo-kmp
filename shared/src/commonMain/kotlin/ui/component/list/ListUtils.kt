/*
 * ListUtils.kt
 * 02.07.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package ui.component.list

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.generic_error_text
import org.jetbrains.compose.resources.stringResource

fun LazyListScope.showLoadingStates(lazyPagingItems: LazyPagingItems<*>) {


    when (lazyPagingItems.loadState.refresh) { //FIRST LOAD
        is LoadState.Error -> {
            val error = lazyPagingItems.loadState.refresh as LoadState.Error
            item {
                ErrorItem(modifier = Modifier.fillParentMaxSize(),
                    message = error.error.message
                        ?: stringResource(Res.string.generic_error_text),
                    onClickRetry = { })
            }
        }

        is LoadState.Loading -> { // Loading UI
            item {
                LoadingItem(
                    modifier = Modifier
                        .fillParentMaxSize()
                )
            }
        }

        else -> {}
    }

    when (lazyPagingItems.loadState.append) { // Pagination
        is LoadState.Error -> {
            val error = lazyPagingItems.loadState.append as LoadState.Error
            item {
                ErrorItem(modifier = Modifier.fillParentMaxSize(),
                    message = error.error.message
                        ?: stringResource(Res.string.generic_error_text),
                    onClickRetry = { })
            }
        }

        is LoadState.Loading -> { // Pagination Loading UI
            item {
                LoadingItem(
                    modifier = Modifier
                        .fillParentMaxSize()
                )
            }
        }

        else -> {}
    }
}