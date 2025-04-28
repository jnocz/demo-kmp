/*
 * GifImage.kt
 * 29.06.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    imageUri: String
) {
    CoilImage(
        imageModel = {
            imageUri
        }, // URL of an animated image.
        //imageLoader = { imageLoader },
        modifier = modifier
            .padding(8.dp)

    )
}