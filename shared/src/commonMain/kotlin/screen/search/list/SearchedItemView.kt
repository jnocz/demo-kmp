/*
 * SearchedItemView.kt
 * 14.11.2023
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2023. All rights reserved.
 *
 */

package screen.search.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.calendar_month
import bazarsearchmultiplatform.shared.generated.resources.close_circle_outline
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_outline
import bazarsearchmultiplatform.shared.generated.resources.ic_heart_red
import bazarsearchmultiplatform.shared.generated.resources.location_on
import bazarsearchmultiplatform.shared.generated.resources.logo
import bazarsearchmultiplatform.shared.generated.resources.payments
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import engine.domain.SearchResult
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.BazarSearchTheme
import ui.theme.color.AppColorPalette

@Preview
@Composable
fun SearchedItemViewPreview() {
    BazarSearchTheme {
        SearchedItemView(
            SearchResult(
                price = "1000 Kč",
                insertedDateOnBazaar = "12.3.2023",
                location = "Olomouc",
                imageSrc = "https://www.bazos.cz/img/1t/799/177052799.jpg?t=1699884365",
                description = "Description ".repeat(20),
                name = "Audi Bmw",
            ), false, {}, {}
        )
    }
}

@Composable
fun SearchedItemView(
    result: SearchResult,
    isFavoriteView: Boolean = false,
    onClickListener: (String?) -> Unit,
    onFavoriteClickListener: (SearchResult) -> Unit
) {

    var isFavorite by remember { mutableStateOf(result.isFavorite) }

    val heartImage =
        when {
            !isFavoriteView ->
                if (isFavorite) {
                    Res.drawable.ic_heart_red
                } else {
                    Res.drawable.ic_heart_outline
                }
            else -> {
                Res.drawable.close_circle_outline
            }
        }

    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, AppColorPalette.black),
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClickListener.invoke(result.link) }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .wrapContentHeight(unbounded = false)
            //.verticalScroll(rememberScrollState())
            //.border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
        ) {
            // Create references for the composables to constrain
            val (image, infoRow, infoColumn) = createRefs()

            val horizontalCenterGuideline = createGuidelineFromTop(0.65f)

            val bottomGuideline = createGuidelineFromBottom(0.0f)

            if (!result.imageSrc.isNullOrBlank()) {
                SearchedItemImage(image, horizontalCenterGuideline, result.imageSrc!!)
            } else {
                DefaultImageIfImageNotExists(image, horizontalCenterGuideline)
            }



            Column(modifier = Modifier
                //.background(Color.Red)
                .padding(16.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .constrainAs(infoColumn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }) {

                if (!result.price.isNullOrBlank()) {
                    InfoCard(Res.drawable.payments, result.price?.trim() ?: "")
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (!result.location.isNullOrBlank()) {
                    InfoCard(Res.drawable.location_on, result.location?.trim() ?: "")
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (!result.insertedDateOnBazaar.isNullOrBlank()) {
                    InfoCard(Res.drawable.calendar_month, result.insertedDateOnBazaar?.trim() ?: "")
                }
            }

            Row(modifier = Modifier
                .constrainAs(infoRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(horizontalCenterGuideline)
                    bottom.linkTo(bottomGuideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }) {

                Column(
                    modifier = Modifier
                        .width(70.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painter = painterResource(heartImage),
                        contentDescription = "Favorites",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            //.background(Color.Yellow)
                            .padding(0.dp)
                            // Set image size to 40 dp
                            .size(40.dp)
                            .clickable {
                                isFavorite = !isFavorite
                                result.isFavorite = !result.isFavorite
                                onFavoriteClickListener(result)
                            }
                        //.fillMaxHeight()
                        // Clip image to be shaped as a circle
                        //.clip(CircleShape)
                        //.border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight().padding(end = 12.dp)
                ) {
                    if (!result.name.isNullOrBlank()) {
                        Text(
                            text = result.name?.trim() ?: "",
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            maxLines = 1
                        )
                    }
                    if (!result.description.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = result.description?.trim() ?: "",
                            modifier = Modifier.padding(end = 5.dp),
                            lineHeight = 15.sp,
                            maxLines = 3,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun InfoCard(resId: DrawableResource, text: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(resId),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .padding(0.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text.trim(),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(2.dp),
            )
        }
    }
}

@Composable
fun ConstraintLayoutScope.SearchedItemImage(
    image: ConstrainedLayoutReference,
    horizontalCenterGuideline: ConstraintLayoutBaseScope.HorizontalAnchor,
    imageUrl: String
) {
    CoilImage(loading = {
        Box(modifier = Modifier.matchParentSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }, failure = {
//            Box(modifier = Modifier.matchParentSize()) {
//                Text(
//                    modifier = Modifier.align(Alignment.Center),
//                    text = stringResource(Res.string.events_image_request_failed),
//                    textAlign = TextAlign.Center
//                )
//            }
    },
        //modifier = boxModifier.size(365.dp, 249.dp).clip(RoundedCornerShape(18.dp)),
        modifier = Modifier
            .height(200.dp)
            .constrainAs(image) {
                // top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(horizontalCenterGuideline)
                width = Dimension.fillToConstraints
                // height = Dimension.fillToConstraints
            },

//
        imageModel = { imageUrl }, // loading a network image or local resource using an URL.
        imageOptions = ImageOptions(
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            requestSize = IntSize(800, 600)
        ), component = rememberImageComponent {

            // add a crossfade animation before drawing the image.
            +CrossfadePlugin(
                duration = 550
            )
        },
        //IN PREVIEW MODE - @Preview
        previewPlaceholder = painterResource(Res.drawable.logo)
    )
}


@Composable
fun ConstraintLayoutScope.DefaultImageIfImageNotExists(
    imageRef: ConstrainedLayoutReference,
    horizontalCenterGuideline: ConstraintLayoutBaseScope.HorizontalAnchor
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.height(200.dp).constrainAs(imageRef) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(horizontalCenterGuideline)
        width = Dimension.fillToConstraints
        // height = Dimension.fillToConstraints
    }) {
        Image(
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.FillHeight,
            painter = painterResource(Res.drawable.logo),
            contentDescription = null
        )
    }
}




