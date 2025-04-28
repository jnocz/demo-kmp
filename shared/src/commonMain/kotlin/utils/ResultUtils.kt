/*
 * ResultUtils.kt
 * 17.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package utils


data class Result<out T> constructor(
    val state: ResultState,
    val data: T? = null,
    val message: String? = null,
    val error: Throwable? = null
)

sealed class ResultState {
    object INIT : ResultState()
    object BLANK : ResultState()
    object LOADING : ResultState()
    object SUCCESS : ResultState()
    object ERROR : ResultState()
    object NETWORK_ERROR : ResultState()
}