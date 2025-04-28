/*
 * AboutViewModel.kt
 * 24.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.about

import BazarSearchMultiplatform.shared.BuildConfig
import screen.base.BaseViewModel
import di.AppCoroutineDispatchers

class AboutViewModel(val dispatchers: AppCoroutineDispatchers) : BaseViewModel() {
    fun getVersionNumber() = BuildConfig.VERSION_NAME
}