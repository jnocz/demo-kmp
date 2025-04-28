/*
 * BaseViewModel.kt
 * 22.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package screen.base

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger

abstract class BaseViewModel :
    ViewModel() {

    override fun onCleared() {
        Logger.i { "Called ViewModel onCleared!" }
        super.onCleared()
    }
}