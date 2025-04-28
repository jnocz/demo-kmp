package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import bazarsearchmultiplatform.shared.generated.resources.Res
import bazarsearchmultiplatform.shared.generated.resources.author_email
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import sh.calvin.autolinktext.TextMatcher
import sh.calvin.autolinktext.TextRule
import sh.calvin.autolinktext.rememberAutoLinkText

@Composable
fun TextWithLink(textId: StringResource) {
    TextWithLink(text = stringResource(textId))
}

@Composable
fun TextWithLink(text: String) {

    val uriHandler = LocalUriHandler.current

    FlowRow {
        for (s in text.split(' ')) {
            if (s.matches(".*(#\\w+)|(http(s)?://.+).*".toRegex())) {
                ClickableText(
                    text = AnnotatedString("$s "), onClick = {
                        runCatching {
                            uriHandler.openUri(s)
                        }
                    }, style = TextStyle(
                        color = Color.Blue,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                    )
                )
            } else {
                Text(text = "$s ")
            }
        }
    }
}


@Composable
fun SimpleAutoLinkText(textId: StringResource) {

    Text(
        AnnotatedString.rememberAutoLinkText(
            text = stringResource(textId),
            defaultLinkStyles = TextLinkStyles(
                SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            )
        )
    )
}

@Composable
fun AboutScreenEvaluateAutoLinkText(textId: StringResource) {

    val email = stringResource(Res.string.author_email)

    Text(
        AnnotatedString.rememberAutoLinkText(
            text = stringResource(textId),
//            defaultLinkStyles = TextLinkStyles(
//                SpanStyle(
//                    color = Color.Blue,
//                    textDecoration = TextDecoration.Underline
//                )
            //),
            textRules = listOf(
//                TextRule.Styleable(
//                    textMatcher = TextMatcher.RegexMatcher(Regex("https\\w+")),
//                    styles = TextLinkStyles(
//                        SpanStyle(
//                            color = Color.Blue,
//                            textDecoration = TextDecoration.Underline
//                        )
//                    )
//                ),
                TextRule.Url(
                    textMatcher = TextMatcher.StringMatcher("Play store"),
                    styles = TextLinkStyles(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    urlProvider = { "https://play.google.com/store/apps/details?id=eu.bazarsearch.app" }
                ),
                TextRule.Url(
                    textMatcher = TextMatcher.StringMatcher("Buy me a coffee"),
                    styles = TextLinkStyles(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    urlProvider = { "https://www.buymeacoffee.com/bazarsearch" }
                ),
                TextRule.Url(
                    textMatcher = TextMatcher.StringMatcher("E-mail"),
                    styles = TextLinkStyles(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    urlProvider = { "mailto:$email" }
                )
            )
        )
    )
}

@Composable
fun CustomTextLink(modifier: Modifier = Modifier, textId: StringResource, onClick: () -> Unit) {
    Box(
        modifier.clickable(
            enabled = true,
            onClick = { onClick() })
    ) {
        Text(
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            text = stringResource(textId)
        )
    }
}