package utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import co.touchlab.kermit.Logger
import eu.bazarsearch.app.shared.R
import ui.theme.color.AppColorPalette


@Composable
actual fun OpenBrowserUrl(url: String) {
    val color: Int = AppColorPalette.lightGrayInt
    val context = LocalContext.current
    var opened = false
    opened = context.openCustomTabUrl(url, color)
    if (!opened) {
        opened = context.openLegacyUrl(url)
    }
}

fun Context.color(colorRes: Int): Int = ContextCompat.getColor(this, colorRes)


private fun Context.openCustomTabUrl(url: String, color: Int): Boolean {
    val intentBuilder = CustomTabsIntent.Builder()
    val defaultColors = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(color)
        .build()
    intentBuilder.setDefaultColorSchemeParams(defaultColors)

    intentBuilder.setShowTitle(true)
    intentBuilder.setUrlBarHidingEnabled(true)
    intentBuilder.setCloseButtonIcon(
        BitmapFactory.decodeResource(this.resources, R.drawable.ic_arrow_back_bmp)
    )
    return openCustomTab(intentBuilder.build(), Uri.parse(url))
}

private fun Context.openLegacyUrl(url: String): Boolean {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (resolveIntent(intent) != null) {
        startActivity(intent)
        return true
    }
    return false
}

fun Context.resolveIntent(intent: Intent): ComponentName? =
    intent.resolveActivity(this.packageManager)


private fun Context.openCustomTab(
    customTabsIntent: CustomTabsIntent,
    uri: Uri
): Boolean {

    //If we cant find a package name, it means theres no browser that supports
    //Chrome Custom Tabs installed. So, we fallback to the webview

    val packageName = getPackageNameToUse(this)
    if (packageName != null) {
        customTabsIntent.intent.`package` = packageName
        customTabsIntent.launchUrl(this, uri)
        return true
    }

    return false

}

/**
 * Used to check whether there is a specialized handler for a given intent.
 * @param intent The intent to check with.
 * *
 * @return Whether there is a specialized handler for the given intent.
 */
private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
    try {
        val pm = context.packageManager
        val handlers = pm.queryIntentActivities(
            intent,
            PackageManager.GET_RESOLVED_FILTER
        )
        if (handlers == null || handlers.size == 0) {
            return false
        }
        for (resolveInfo in handlers) {
            val filter = resolveInfo.filter ?: continue
            if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
            if (resolveInfo.activityInfo == null) continue
            return true
        }
    } catch (e: RuntimeException) {
        Logger.e(e) { "Runtime exception while getting specialized handlers" }
    }

    return false
}

/**
 * Goes through all apps that handle VIEW intents and have a warmup service. Picks
 * the one chosen by the user if there is one, otherwise makes a best effort to return a
 * valid package name.

 * This is **not** threadsafe.

 * @param context [Context] to use for accessing [PackageManager].
 * *
 * @return The package name recommended to use for connecting to custom tabs related components.
 */
fun getPackageNameToUse(context: Context): String? {
    val pm = context.packageManager
    // Get default VIEW intent handler.
    val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
    val defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0)
    var defaultViewHandlerPackageName: String? = null
    if (defaultViewHandlerInfo != null) {
        defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
    }

    // Get all apps that can handle VIEW intents.
    val ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService"
    val resolvedActivityList = pm.queryIntentActivities(activityIntent, 0)
    val packagesSupportingCustomTabs = ArrayList<String>()
    for (info in resolvedActivityList) {
        val serviceIntent = Intent()
        serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
        serviceIntent.`package` = info.activityInfo.packageName
        if (pm.resolveService(serviceIntent, 0) != null) {
            packagesSupportingCustomTabs.add(info.activityInfo.packageName)
        }
    }

    val STABLE_PACKAGE = "com.android.chrome"
    val BETA_PACKAGE = "com.chrome.beta"
    val DEV_PACKAGE = "com.chrome.dev"
    val LOCAL_PACKAGE = "com.google.android.apps.chrome"

    // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
    // and service calls.
    if (packagesSupportingCustomTabs.isEmpty()) {
        return null
    } else if (packagesSupportingCustomTabs.size == 1) {
        return packagesSupportingCustomTabs[0]
    } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
        && !hasSpecializedHandlerIntents(context, activityIntent)
        && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)
    ) {
        return defaultViewHandlerPackageName
    } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
        return STABLE_PACKAGE
    } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
        return BETA_PACKAGE
    } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
        return DEV_PACKAGE
    } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
        return LOCAL_PACKAGE
    }
    return null
}


fun Activity.launchWebBrowser(url: String, fallbackUrl: String) {
    val transformedUrl = url.trim()
    val transformedFallbackUrl = fallbackUrl.trim()

    val intent = if (transformedUrl.startsWith("instagram://")) {
        val tryIntent = Intent(Intent.ACTION_VIEW, Uri.parse(transformedUrl))
        tryIntent.`package` = "com.instagram.android"
        when {
            resolveIntent(tryIntent) != null -> tryIntent
            else -> Intent(Intent.ACTION_VIEW, Uri.parse(transformedFallbackUrl))
        }
    } else {
        Intent(Intent.ACTION_VIEW, Uri.parse(transformedFallbackUrl))
    }

    startActivity(intent)
}